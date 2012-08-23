(ns incanterdemos.test.timestamp
  (:use clojure.test)
  (:use incanterdemos.timestamp) ; code under test
  (:require  [incanter.core :as incanter]
	     [clj-time.core :as clj-time]))

(deftest test-fix-timestamp ; fix incanter bug
  (let [ ds (incanter/dataset [:ts] [ {:ts "1330418007548"} {:ts "1330418007549"} ]) ]
    (is (= 1330418007548
	 (incanter/sel (fix-timestamp ds) :rows 0 :cols :tsn ) ))))

(deftest test-readable-timestamp
  (let [ ds (incanter/dataset [:ts] [ {:ts 1330418007548} {:ts 1330418007549} ]) ]
    (is (= 2012
	 (clj-time/year (incanter/sel (readable-timestamp ds) :rows 0 :cols :timestamp ) )))))

