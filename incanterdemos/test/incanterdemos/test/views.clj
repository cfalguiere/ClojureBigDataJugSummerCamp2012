(ns incanterdemos.test.views
  (:use clojure.test)
  (:use incanterdemos.views) ; code under test
  (:require  [incanter.core :as incanter]
	     [clj-time.core :as clj-time]))

(deftest test-timestamp
  (let [ ds (incanter/dataset [:ts] [ {:ts "1330418007548"} {:ts "1330418007549"} ]) ]
    (is (= 2012
	 (clj-time/year (incanter/sel (timestamp ds) :rows 0 :cols :timestamp ) )))))

