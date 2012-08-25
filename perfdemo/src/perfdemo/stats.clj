(ns perfdemo.stats
  (:use (incanter core))
  (:require [clojure.algo.generic.functor :as functor]
	    [incanter.stats :as stats]
	    [incanter.io :as incanterio]) )

(defn dataset-stats-vector [ds]
  (let [ series ($ :t ds) ]
    [ (count series) (stats/mean series) (apply min series) (apply max series)
      (stats/quantile series :probs 0.95)] ))

(defn group-stats-table [ds]
  ($order :lb :asc (dataset [:lb :count :mean :min :max :q95]
	 (vals (functor/fmap
		  #(flatten (conj [(sel % :rows 0 :cols :lb)]
				  (dataset-stats-vector % )))
		  ($group-by :lb ds))))))

