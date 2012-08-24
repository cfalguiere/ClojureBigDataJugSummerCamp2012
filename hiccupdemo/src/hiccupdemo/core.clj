(ns hiccupdemo.core
  (:use [hiccup.core])
  (:use [hiccup.page])
  (:use [hiccupdemo.stats])
  (:use [clojure.java.io :only (file copy)])
  (:require [incanter.core :as incanter]
	    [incanter.io :as incanterio]
	    [clojure.algo.generic.functor :as functor]
	    ))

;;; Noir macro
(defmacro defpartial 
  "Create a function that returns html using hiccup. The function is 
   callable with the given name."
  [fname params & body]
  `(defn ~fname ~params
     (html
      ~@body)))

(defpartial table-headers []
  [:tr
   [:th "Label" ]
   [:th "Nombre" ]
   [:th "Moyenne" ]])
   

(defpartial table-row [{:keys [lb count mean]}] ;;destructured equiv to [{label :lb, count :count ...
  [:tr
   [:td lb ]
   [:td count ]
   [:td mean ]])


(defn build-table [{:keys [rows]}]
    (html [:table
	   (table-headers) 
	   (map table-row rows)
	   ]))

(defn build-page [stats]
  (let [ table (build-table stats) ]
    (html
     (html5
      [:head 
       (include-css "css/report.css")]
      [:body
       table]
       ))))



(defn save-page [s]
  (spit "output/report.html" s))

(defn copy-file [source-path dest-path]
  (copy (file source-path) (file dest-path)))
 

(defn -main [& args]
					;  (let [ds (incanterio/read-dataset  "resources/data/readings.csv" :header true)]
  (let [ds (incanter/dataset [:lb :count :mean] [{:lb "A" :mean 2.5 :count 3}])]
    (save-page (build-page ds))
    (copy-file "resources/css/report.css" "output/css/report.css")
    ))
