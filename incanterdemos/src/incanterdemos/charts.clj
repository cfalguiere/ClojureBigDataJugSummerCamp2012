(ns incanterdemos.charts
  (:use (incanter core))
  (:use incanterdemos.timestamp) 
  (:require [clojure.algo.generic.functor :as functor]
	    [incanter.io :as incanterio]
	    [incanter.stats :as stats]
	    [incanter.charts :as charts]
	    [clj-time.core :as clj-time]) )


(defn  trend [ds start-time end-time]
  (with-data ($where { :timestamp {:$gt start-time  :$lt end-time }} ds)
    (def lm (stats/linear-model ($ :t) ($ :tsn)))
    (doto (charts/time-series-plot  :tsn :t )
      (charts/add-lines ($ :tsn) (:fitted lm))
      view)))

(defn -main [& args]
  (let [ds (incanterio/read-dataset  "resources/data/readings.csv" :header true)
	start-time (clj-time/date-time 2012 02 28 8 55 15)
	end-time (clj-time/date-time 2012 02 28 8 57 20) ]
    (trend (obj-timestamp (fix-timestamp ds)) start-time end-time)))

; lein run -m incanterdemos.charts