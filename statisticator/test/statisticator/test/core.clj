(ns statisticator.test.core
  (:use [statisticator.core])
  (:use [clojure.test]))

(deftest test-wordcount-5
  (is (run job)))
