(ns loaderdemo.test.core
  (:use [loaderdemo.core])
  (:use [clojure.test]))

(deftest test-f1 
  (is 6 (f1 3)))
