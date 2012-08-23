(ns incanterdemos.views
  (:use (incanter core))
  (:use incanterdemos.timestamp) 
  (:require [clojure.algo.generic.functor :as functor]
	    [incanter.core :as incanter]
	    [incanter.io :as incanterio]
	    [clj-time.core :as clj-time]
	    [clj-time.coerce :as coerce]) )

 
(defn -main [& args]
  (let [ds (incanterio/read-dataset  "resources/data/readings.csv" :header true)
	start-time (clj-time/date-time 2012 02 28 8 55 15)
	end-time (clj-time/date-time 2012 02 28 8 57 20) ]
    (with-data (obj-timestamp (fix-timestamp ds))
      (view ($ [:lb :t :shop :hrts] ($where {:shop "MAF"
	       :timestamp {:$gt start-time  :$lt end-time}} ))))))

; lein run -m incanterdemos.views