(ns incanterdemos.test.stats
  (:use clojure.test)
  (:use incanterdemos.stats) ; code under test
  (:require  [incanter.core :as incanter]))

(deftest test-series-summary
  (let [ series [ 1 2 ] ]
    (is (= { :count 2 :mean 1.5 :sd 0.7071067811865476 :min 1 :max 2 :q50 1.5 :q95 1.9 }
	 (series-summary series) ))))


(deftest test-dataset-stats-vector
  (let [ ds (incanter/dataset [:lb :t] [ {:lb "A" :t 1} {:lb "A" :t 2} ]) ]
    (is (= [2 1.5]
	 (dataset-stats-vector ds) ))))

(deftest test-group-stats-table
  (let [ ds (incanter/dataset [:lb :t] [ {:lb "A" :t 1} {:lb "A" :t 9} {:lb "B" :t 2} {:lb "B" :t 6}])
	result (group-stats-table ds)  ]
    (is (= "A" 
	 (incanter/sel result :rows 0 :cols :lb)))
    (is (= 5.0
	 (incanter/sel result :rows 0 :cols :mean)))
    (is (= 2
	 (incanter/sel result :rows 0 :cols :count)))))

