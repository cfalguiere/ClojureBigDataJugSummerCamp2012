(ns statisticator
  (:use (incanter core stats))
  (:require [clojure.contrib.generic.functor :as functor]
	    [clj-time.coerce :as coerce]) )

(load "jmeter-loader-util")

(time (def ds (read-dataset-jmeter-file  "data/sample-1h-m1.jtl") ))

(def ds (conj-cols ds
		   (col-names ($map #(coerce/from-long %) :ts ds )[:hrts]) ))

;;; count entries
(nrow ds)

;;; compute mean
(mean ($ :t ds))

;;; compute many indicators
[ (count ($ :t ds)) (mean ($ :t ds)) (sd ($ :t ds)) ]
;; cleaner
(with-data ($ :t ds)
  [ (count $data) (mean $data) (sd $data) (quantile $data :probs[0.95]) ] ) 
;; insert labels
(with-data ($ :t ds)
  (zipmap [ "count" "mean" "sd" "q95"]
	  [ (count $data) (mean $data) (sd $data) (quantile $data :probs[0.95]) ] )) 

;;; groups
(sort (into #{} ($ :lb ds)))
(keys ($group-by :lb ds))

;;; dataset for a group
(view (get ($group-by :lb ds) {:lb "SU01-HomePage-Page"} ))

;; number of readings per group
(functor/fmap nrow ($group-by :lb ds))

;; stats per group
(functor/fmap
   #(with-data ($ :t %)
      (zipmap [ "mean" "sd"] [ (mean $data) (sd $data) ] )) 
   ($group-by :lb ds))

;; TODO ignore errors - lacking data with errors


