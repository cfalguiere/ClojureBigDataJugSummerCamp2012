(ns incanterdemos.test.timestamp
  (:use clojure.test)
  (:use incanterdemos.timestamp) ; code under test
  (:require  [incanter.core :as incanter]
	     [clj-time.core :as clj-time]))

(deftest test-readable-timestamp
  (let [ ds (incanter/dataset [:ts] [ {:ts 1330418007548} {:ts 1330418007549} ])
	 result  (readable-timestamp ds)  ]
    (is (= 2012
	(clj-time/year (incanter/sel result :rows 0 :cols :timestamp ) )))))

