(ns hadoopdemo.test.core
  (:require [clojure-hadoop.imports :as imp] )
  (:use [hadoopdemo.core])
  (:use [clojure.test]))

(imp/import-io)
(imp/import-mapreduce)

(deftest test-csv-line-parser 
  (is (=
       ("976","SU01-HomePage-Page","1330418007548","true","PT1022809331","200","FAB","bac plastique","")
      (csv-line-parser "976,SU01-HomePage-Page,1330418007548,true,PT1022809331,200,FAB,bac plastique,"))))

(deftest test-my-map
  (is (=
       [ ["all" 976] ["SU01-HomePage-Page" 976] ["FAB" 976] ["bac plastique" 976]]
      (my-map 1 ("976","SU01-HomePage-Page","1330418007548","true","PT1022809331","200","FAB","bac plastique","")))))


