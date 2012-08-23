(ns incanterdemos.save
  (:use (incanter core))
  (:use incanterdemos.timestamp) 
  (:require [clojure.algo.generic.functor :as functor]
	    [incanter.io :as incanterio]
	    [incanter.stats :as stats]
	    [incanter.charts :as charts]
	    [clj-time.core :as clj-time]) )


(defn  save-all [ds]
       (save ds "output/readings.csv")
       (save ($rollup stats/mean :t :lb ds) "output/stats.csv")
       (save (charts/time-series-plot :tsn :t :data ds) "output/ts.png")) 

(defn -main [& args]
  (let [ds (incanterio/read-dataset  "resources/data/readings.csv" :header true) ]
    (save-all (fix-timestamp ds))))

; lein run -m incanterdemos.charts