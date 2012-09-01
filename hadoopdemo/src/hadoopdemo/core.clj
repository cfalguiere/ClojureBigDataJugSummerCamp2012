(ns hadoopdemo.core
  (:require [clojure-hadoop.wrap :as wrap]
            [clojure-hadoop.defjob :as defjob]
            [clojure-hadoop.imports :as imp]
	    [clojure-csv.core :as csv] 
	    [incanter.stats :as stats] )
  (:import (java.util StringTokenizer))
  (:use clojure.test clojure-hadoop.job))


(imp/import-io)
(imp/import-mapreduce)

(defn csv-line-parser [line]
  (map #(.trim %) (first (csv/parse-csv line))))

(defn csv-map-reader [#^LongWritable wkey #^Writable wline]
  (println ">>>>csv-map-reader: wkey" wkey)
  (println ">>>>csv-map-reader: wvalue" (.toString wline))
  [(.get wkey) (csv-line-parser (.toString wline))])

(defn my-map [key cells]
  (println ">>>>my-map: key " key)
  (println ">>>>my-map: cells " cells)
  (cond
    (= key 0) []
    :else (let [ t (Long/parseLong (nth cells 0))
		[lb, shop, kw] (map #(nth cells %) [1,6,7])
		cat (map #( [% t] ) [lb, shop, kw])]
	    [["all" t] [(str "lb:" lb) t] [(str "shop:" shop) t] [(str "shop:" kw) t]] )))

(defn string-long-map-writer [^TaskInputOutputContext context ^String key measure] 
  (println ">>>>string-long-map-writer: key " key)
  (println ">>>>string-long-map-writer: measure " measure)
  (.write context (Text. key) (LongWritable. measure)))

(defn string-long-reduce-reader [^Text key wvalues]
  (println "string-long-reduce-reader: key " key)
  (println "string-long-reduce-reader: wvalues " wvalues)
  [(.toString key)
   (fn [] (map (fn [^LongWritable v] (.get v)) wvalues))])

(defn my-reduce [key values-fn]
  (println ">>>>my-reduce: key" key)
  (println ">>>>my-reduce: values-fn" values-fn)
  (let [measures (values-fn)]
    [[(str key ":count") (count measures)]
     [(str key ":mean") (stats/mean measures)]]))

(defn string-long-reduce-writer [^TaskInputOutputContext context ^String key value]
  ;(println ">>>>string-long-reduce-writer context" context)
  (println ">>>>string-long-reduce-writer: key" key)
  (println ">>>>string-long-reduce-writer: value" value)
  (.write context (Text. key) (LongWritable. value)))


(defjob/defjob job
  :input-format :text
  ;:input "resources/sample-text.txt"
;  :input "resources/sample-readings.csv"
  :input "resources/readings.csv"
  :map my-map
;  :map-reader wrap/int-string-map-reader
  :map-reader csv-map-reader
  :map-writer string-long-map-writer
  :reduce my-reduce
  :reduce-reader string-long-reduce-reader
  :reduce-writer string-long-reduce-writer
  :output-key Text
  :output-value LongWritable
  :output-format :text
  :output "tmp/out5"
  :compress-output false
  :replace true)

(defn -main [& args]
  (run job))

