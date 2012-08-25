(ns perfdemo.report
  (:use [hiccup.core])
  (:use [hiccup.page])
  (:use [hiccup.element])
  (:use [clojure.java.io :only (file copy)])
  (:require 
	    [incanter.core :as incanter]
	    [incanter.io :as incanterio]
	    [incanter.charts :as charts]
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
   [:th "Count" ]
   [:th "Mean" ]
   [:th "Min" ]
   [:th "Max" ]
   [:th "Quantile95" ]])
   

(defpartial table-row [{:keys [lb count mean min max q95]}] ;;destructured equiv to [{label :lb, count :count ...
  [:tr
   [:td lb ]
   [:td {:class "number"} count ]
   [:td {:class "number"} (format "%6.2f" mean) ]
   [:td {:class "number"} min ]
   [:td {:class "number"} max ]
   [:td {:class "number"} (format "%6.2f" q95) ]])


(defn build-table [{:keys [rows]}]
    (html [:table
	   (table-headers) 
	   (map table-row rows)
	   ]))

(defn build-page [{:keys [tsplot-url bar-url stats]}]
    (html
     (html5
      [:head 
       (include-css "css/report.css")]
      [:body
        (image tsplot-url "Response Time plot")
        (image bar-url "Number by shop")
        (build-table stats)]
       )))

(defn generate-chart [ds filename]
  (let [ url (str "charts/" filename)
	chart (charts/bar-chart :shop :t :vertical false
			:y-label "Count" :x-label "Shop"
		 :data  (incanter/$rollup count  :t :shop ds)) ]
	 (incanter/save chart (str "output/" url))
	 url))

(defn generate-ts-plot [ds filename]
  (let [ url (str "charts/" filename)
	chart (charts/time-series-plot :ts :t :data ds  :title "Readings"
			         :x-label "time"  :y-label "resp. time (ms)"
		           	 :legend true  :series-label "duration ms") ]
	 (incanter/save chart (str "output/" url))
	 url))

(defn save-page [s]
  (spit "output/report.html" s))

(defn copy-file [source-path dest-path]
  (copy (file source-path) (file dest-path)))
 

