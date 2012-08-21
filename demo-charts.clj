(ns statisticator
  (:use (incanter core stats charts))
  (:require [clojure.contrib.generic.functor :as functor]
	    [clj-time.coerce :as coerce]
	    [clj-time.core :as clj-time]) )

(load "jmeter-loader-util")

(time (def ds (read-dataset-jmeter-file  "data/sample-1h-m1.jtl") ))

(def ds (conj-cols ds
		   (col-names ($map #(coerce/from-long %) :ts ds )[:hrts]) ))


;;; time series of resp time
(view (time-series-plot :ts :t :data ds))

(doto  (time-series-plot :ts :t :data ds :title "Readings"
			 :x-label "time" :y-label "resp. time (ms)"
			 :legend true :series-label "duration ms"
			 )
    (set-stroke-color java.awt.Color/blue)
    view)

;;; histogram of resp time
(view (histogram :t :data ds) )


;;; bar-chart count per shop
(view (bar-chart :shop :t :vertical false
		 :data  ($rollup count :t :shop ds)))

;;; box-plot 
(view (box-plot :t :group-by :shop :data ds))

;;; complex plot
(def px (coerce/to-long (clj-time/date-time 2012 02 28 10 25)))
(def py 12000)
(def ts (sel ds :cols :ts))

(doto  (time-series-plot :ts :t :data ds :title "Readings"
			 :x-label "time" :y-label "resp. time (ms)"
			 :legend true :series-label "duration ms"
			 )
  (add-lines  ts (repeat 3000) :series-label "threshold")
  (add-polygon [[px py] [(+ px 100) (+ py 100)] [(- px 100) (+ py 10)]])
  (add-pointer px py  :text "mais pourquoi ?" :angle :east) ;;TODO
  (set-stroke-color java.awt.Color/blue :series 0) 
  (set-stroke :width 1 :series 0) 
  (set-stroke-color java.awt.Color/red :series 1) 
  (set-stroke :width 3 :series 1) 
  (set-stroke-color java.awt.Color/yellow :series 2) 
  (set-stroke :width 5 :series 2) 
    view)

;;; trend
(with-data ($where { :hrts {:$gt (clj-time/date-time 2012 02 28 8 55 15)
			   :$lt (clj-time/date-time 2012 02 28 8 57 20) }} ds)
  (def lm (linear-model ($ :t) ($ :ts)))
  (doto (time-series-plot  :ts :t )
    (add-lines ($ :ts) (:fitted lm))
    view))
