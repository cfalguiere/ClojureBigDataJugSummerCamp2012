(ns hiccupdemo.stats
  (:use (incanter core))
  (:require [clojure.algo.generic.functor :as functor]
	    [incanter.stats :as stats]
	    [incanter.io :as incanterio]) )

(defn dataset-stats-vector [ds]
  (with-data ($ :t ds)
    [ (count $data) (stats/mean $data) ] ))

(defn group-stats-table [ds]
   (dataset [:lb :count :mean]
	 (vals (functor/fmap
		  #(flatten (conj [(sel % :rows 0 :cols :lb)]
				  (dataset-stats-vector % )))
		  ($group-by :lb ds)))))

