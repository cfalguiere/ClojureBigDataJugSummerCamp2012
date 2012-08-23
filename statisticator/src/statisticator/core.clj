(ns statisticator.core
  (:require [clojure-hadoop.wrap :as wrap]
            [clojure-hadoop.defjob :as defjob]
            [clojure-hadoop.imports :as imp])
  (:import (java.util StringTokenizer))
  (:use clojure.test clojure-hadoop.job))


(imp/import-io)
(imp/import-mapreduce)

(defn my-map [key value]
  (println "my-map" key value)
  (map (fn [token] [token 1]) (enumeration-seq (StringTokenizer. value))))

(defn my-reduce [key values-fn]
  (println "my-reduce" key values-fn)
  [[key (reduce + (values-fn))]])

(defn string-long-writer [^TaskInputOutputContext context ^String key value]
  (println "string-long-writer" key values-fn)
  (.write context (Text. key) (LongWritable. value)))

(defn string-long-reduce-reader [^Text key wvalues]
  (println "string-long-reduce-reader" key wvalues)
  [(.toString key)
   (fn [] (map (fn [^LongWritable v] (.get v)) wvalues))])

(defjob/defjob job
  :map my-map
  :map-reader wrap/int-string-map-reader
  :map-writer string-long-writer
  :reduce my-reduce
  :reduce-reader string-long-reduce-reader
  :reduce-writer string-long-writer
  :output-key Text
  :output-value LongWritable
  :input-format :text
  :output-format :text
  :compress-output false
  :input "data/sample-text.txt"
  :output "tmp/stats"
  :replace true)

(defn -main [& args]
  (run job))