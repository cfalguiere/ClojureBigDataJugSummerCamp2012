(ns incanterdemos.stats
  (:use (incanter core))
  (:require [clojure.algo.generic.functor :as functor]
	    [incanter.core :as incanter]
	    [incanter.stats :as stats]
	    [incanter.io :as incanterio]) )

(defn dataset-stats-vector [ds]
  (incanter/with-data (incanter/$ :t ds)
    [ (count $data) (stats/mean $data) ] ))

(defn group-stats-table [ds]
   (incanter/dataset [:lb :count :mean]
	 (vals (functor/fmap
		  #(flatten (conj [(incanter/sel % :rows 0 :cols :lb)]
				  (dataset-stats-vector % )))
		  (incanter/$group-by :lb ds)))))

(defn -main [& args]
  (let [ds (incanterio/read-dataset  "resources/data/readings.csv" :header true)]
    (println (group-stats-table ds))))

; lein run -m incanterdemos.stats