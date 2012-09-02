(ns incanterdemos.stats
  (:use (incanter core))
  (:require [clojure.algo.generic.functor :as functor]
	    [incanter.stats :as stats]
	    [incanter.io :as incanterio]) )

(defn series-summary [series]
    { :count (count series)
     :mean  (stats/mean series)
     :sd  (stats/sd series)
     :min (apply min series)
     :max (apply max series)
     :q50 (first (stats/quantile series :probs [0.5]))
     :q95 (first (stats/quantile series :probs [0.9])) })


(defn dataset-stats-vector [ds]
  (with-data ($ :t ds)
    [ (count $data) (stats/mean $data) ] ))

(defn group-stats-table [ds]
   (dataset [:lb :count :mean]
	 (vals (functor/fmap
		  #(flatten (conj [(sel % :rows 0 :cols :lb)]
				  (dataset-stats-vector % )))
		  ($group-by :lb ds)))))

(defn -main [& args]
  (let [ds (incanterio/read-dataset  "resources/data/readings.csv" :header true)]
    (println (group-stats-table ds))))

; lein run -m incanterdemos.stats