(ns incanterdemos.views
  (:use (incanter core))
  (:require [clojure.algo.generic.functor :as functor]
	    [incanter.core :as incanter]
	    [incanter.io :as incanterio]
	    [clj-time.core :as clj-time]
	    [clj-time.coerce :as coerce]) )

;(defn timestamp [ds]
;    (conj-cols ds (col-names ($map #(coerce/from-long %) :ts ds )[:timestamp]) ))
;; workaround incanter bug on long conversion
(defn timestamp [ds]
  (incanter/conj-cols ds (incanter/col-names
			  (incanter/$map #(coerce/from-long (Long/parseLong %)) :ts ds )
			  [:timestamp]) ))
 
(defn -main [& args]
  (let [ds (incanterio/read-dataset  "resources/data/readings.csv" :header true)
	start-time (clj-time/date-time 2012 02 28 8 55 15)
	end-time (clj-time/date-time 2012 02 28 8 57 20) ]
    (with-data (timestamp ds)
      (view ($ [:lb :t :shop :hrts] ($where {:shop "MAF"
	       :timestamp {:$gt start-time  :$lt end-time}} ))))))

; lein run -m incanterdemos.views