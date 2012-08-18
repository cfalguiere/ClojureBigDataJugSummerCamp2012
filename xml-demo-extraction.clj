;; test xml
;;  

(ns statisticator
  (:use clojure.test)
  (:use (incanter core io datasets stats))
  (:require [clojure.xml :as xml]))

(def datadir "../data/etalon/") ;;
(def datafilename "20120228-0933_MSALL_TPE2012IBM2.jtl") ;;  3 Mo -> 800
;;(def datadir "../data/6h/") ;;
;;(def datafilename "20120228-1100_MS1_TPS2012IBM2.jtl") ;; 87 Mo -> 8s a 50s ?
(def datafile (str datadir "/" datafilename))
(time (def xmldata (xml/parse datafile)))

(def pagepattern #"SU[^-]+-[^-]+-Page")

		   ;;#(re-matches pagepattern label)
;;(def samples (for [x (xml-seq xmldata)
;;		   :let [label (:ts (:attrs el))] 
;;		   :when  (= :sample (:tag x))  ] 
;;	       (:attrs x)))

;(def samples (map #(:attrs %) (filter #(= :sample (:tag %)) (xml-seq xmldata))))
(defn selectedSamples [el] (and (= :sample (:tag el)) (re-matches pagepattern (:lb (:attrs el)))))  
;(def samples (map #(:lb (:attrs %)) (filter selectedSamples (xml-seq xmldata))))
;(def samples (map #(:attrs %) (filter selectedSamples (xml-seq xmldata))))
(def selectedColumns [:ts :search__phrase :userId :rc :by :productId :shop :lb :t :s])
(def samples (map
	      #(select-keys (:attrs %) selectedColumns)
	      (filter selectedSamples (xml-seq xmldata))))

(defn converNumerics [sample] (update-in sample [:t :ts]  #(Long/parseLong %) ))
(deftest test-converNumerics
  (is (= {:t 2 :ts 2}  (converNumerics {:t "2" :ts "2"}) )) )

(def ds (dataset selectedColumns
		  (map #(select-keys (:attrs %) selectedColumns)
		       (filter selectedSamples (xml-seq xmldata))) ))
                  
($ :t ds)
;(mean (map read-string ($ :t ds)))


;(defn update-columns
;         [dataset columns f]
;         (->> (map #(doseq [col columns] (update-in % [col] f)) (:rows dataset))
;           vec
;           (assoc dataset :rows)))
;(def ds2 (update-columns ds [:t :ts] #(Long/parseLong %))  )

;(mean ($ :t ds))
;(view (time-series-plot :ts :t :data ds));

(run-tests)