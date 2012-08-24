(ns hiccupdemo.test.core
  (:use [hiccupdemo.core])
  (:use [clojure.test])
  (:use [incanter.core]))

(deftest test-build-table
  (let [stats (dataset [:lb :count :mean] [{:lb "A" :mean 2.5 :count 3}])
	result (build-table stats)]
    (println result)
    (is (= "<table><th><td>Label</td><td>Nombre</td><td>Moyenne</td></th><tr><td>A</td><td>3</td><td>2.5</td></tr></table>"
	   result))))

(deftest test-build-page
  (let [result (build-page)]
    (println result)
    (is (= "<!DOCTYPE html>\\n<html><p>test</p></html>"
	   result))))
