;; test xml
;;  
(use '[clojure.contrib.generic.functor :only (fmap)])

(ns statisticator
  (:use clojure.test)
  (:use (incanter core io stats datasets))
  (:require [clojure.xml :as xml]))

(def datadir "../data/etalon/") ;;
(def datafilename "20120228-0933_MSALL_TPE2012IBM2.jtl") ;;  3 Mo -> 800
;;(def datadir "../data/6h/") ;;
;;(def datafilename "20120228-1100_MS1_TPS2012IBM2.jtl") ;; 87 Mo -> 8s a 50s ?
(def datafile (str datadir "/" datafilename))
;(time (def xmldata (xml/parse datafile)))

(def pagepattern #"SU[^-]+-[^-]+-Page")
(defn is-page [sample] (re-matches pagepattern (:lb sample)))

(defn selected-samples-filter [el] (and (= :sample (:tag el)) (is-page (:attrs el)) ))  

(defn auto-format [s] (if (number? (read-string s)) (Long/parseLong s) s ))
	    
(defn extract-data [xml selected-filter columns]
  (map
   #(clojure.contrib.generic.functor/fmap auto-format  (select-keys (:attrs %) columns))
   (filter selected-filter (xml-seq xml))))

(defn read-dataset-xml [xml selected-filter columns]
  (dataset columns (extract-data xml selected-filter columns)))

;(def ds (read-dataset-xml 
;	 (xml/parse "../data/etalon/20120228-0933_MSALL_TPE2012IBM2.jtl")
;	 selected-samples-filter
;	 [:ts :search__phrase :userId :rc :by :productId :shop :lb :t :s]) ) 
                  
;(mean ($ :t ds))
;((view ds)
;(view (time-series-plot :ts :t :data ds))

