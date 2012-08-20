(ns statisticator
  (:use (incanter core stats))
  (:require [clojure.contrib.generic.functor :as functor]
	    [clj-time.coerce :as coerce] ) )

(load "jmeter-loader-util")

(time (def ds (read-dataset-jmeter-file  "data/jmeter-sample.jtl") ))

(def ds (conj-cols ds
		   (col-names ($map #(coerce/from-long %) :ts ds )[:hrts]) ))


;;; time series
(view (time-series-plot :ts :t :data ds))

(doto  (time-series-plot :ts :t :data ds :title "Readings"
			 :x-label "time" :y-label "resp. time (ms)"
			 :legend true :series-label "duration ms"
			 )
    (set-stroke-color java.awt.Color/blue)
    view)

(view (histogram :ts :data ds) )

(def groups  ($group-by :lb ds))
(def counts (functor/fmap nrow groups) )
(def categories (map #(:lb %) (keys counts)))
(view (bar-chart categories (vals counts) :data ds :vertical false))

(view (box-plot (sel ds :cols :t) :group-by (sel ds :cols :lb)))

  (def y (sel ds :cols :t))
  (def x (sel ds :cols :ts))
  (def lm1 (linear-model y x))
(doto  (time-series-plot :ts :t :data ds :title "Readings"
			 :x-label "time" :y-label "resp. time (ms)"
			 :legend true :series-label "duration ms"
			 )
  (add-lines  x (:fitted lm1) :series-label "regression")
  (add-lines  x (repeat 3000) :series-label "max")
  (add-pointer 10  6000 :text "ici" :angle :north) ;;TODO

  (set-stroke-color java.awt.Color/blue ) ;;TODO colors
    view)
