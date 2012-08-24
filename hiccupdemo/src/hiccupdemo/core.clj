(ns hiccupdemo.core
  (:use [hiccup.core])
  (:use [hiccup.page])
  (:use [hiccupdemo.stats])
  (:require [incanter.core :as incanter]
	    [incanter.io :as incanterio]
	    [clojure.algo.generic.functor :as functor]
	    [clojure.java.io :only (file copy)]) )

;;; Noir macro
(defmacro defpartial 
  "Create a function that returns html using hiccup. The function is 
   callable with the given name."
  [fname params & body]
  `(defn ~fname ~params
     (html
      ~@body)))

(defpartial table-headers []
  [:th
   [:td "Label" ]
   [:td "Nombre" ]
   [:td "Moyenne" ]])
   
(defpartial table-row [{:keys [lb count mean]}] ;;destructured equiv to [{label :lb, count :count ...
  [:tr
   [:td lb ]
   [:td count ]
   [:td mean ]])

(defn build-table [stats]
  (html [:table
	 (table-headers) 
	 (map table-row (:rows stats))
	 ]))


(defn build-page []
  (html
   (html5
    [:p "test"])))

(defn save-page [s]
  (spit "output/report.html" s))

(defn copy-file [src-path dest-path]
  (copy (file source-path) (file dest-path)))
 

(defn -main [& args]
					;  (let [ds (incanterio/read-dataset  "resources/data/readings.csv" :header true)]
  (let [ds (incanter/dataset [:lb :count :mean] [{:lb "A" :mean 2.5 :count 3}])]
    (save-page (build-page))
    (copy-file "/resources/report.css" "/output/report.css")
    ))
