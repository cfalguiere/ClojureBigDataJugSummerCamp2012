;;  (:use (incanter core io stats datasets charts))
(ns statisticator
  (:use (incanter core))
  (:require [clojure.contrib.generic.functor :as functor]
	    [clj-time.core :as clj-time]
	    [clj-time.coerce :as coerce]) )

(load "jmeter-loader-util")

(time (def ds (read-dataset-jmeter-file  "data/jmeter-sample.jtl") ))

;;; how many lines ?
(nrow ds)

;;; view data
(view ds)
(head ds)

;;; select columns
(view ($ :lb ds))
(view ($ [:lb :t] ds ))

(view ($order :t :desc ds))


;;; filtering values 
(distinct ($ :shop ds))
(view ($where {:shop "MAF"} ds))
(view ($ :t ($where {:shop "MAF"} ds)))

;; queries with multiple criteria
(view ($where {:shop {:$in #{"MAF", "FAB"}}
	       :t {:$gt 3000 :$lt 5000}} ds))

;;;; add a column and lookup byt date
($map #(coerce/from-long %) :ts ds )

(def hrtsds (col-names ($map #(coerce/from-long %) :ts ds )[:hrts]))
(head hrtsds)

(def ds (conj-cols ds
		   (col-names ($map #(coerce/from-long %) :ts ds )[:hrts]) ))

(view ($ [:lb :t :shop :hrts] ($where {:shop "MAF"
	       :hrts {:$gt (clj-time/date-time 2012 02 28 8 46)
		      :$lt (clj-time/date-time 2012 02 28 8 47)
		    }} ds)))

