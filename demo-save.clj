(ns statisticator
  (:use (incanter core io stats charts)) )

(load "jmeter-loader-util")

(def ds (read-dataset-jmeter-file  "data/sample-1h-m1.jtl") )
(def ds (conj-cols ds
		   (col-names ($map #(coerce/from-long %) :ts ds )[:hrts]) ))

(save ds "data/readings.csv")

(save ($rollup count :t :lb ds) "data/stats.csv")
(save (time-series-plot :ts :t :data ds) "data/ts.png") 