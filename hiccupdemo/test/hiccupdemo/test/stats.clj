(ns hiccupdemo.test.stats
  (:use clojure.test)
  (:use hiccupdemo.stats) ; code under test
  (:require  [incanter.core :as incanter]))

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

