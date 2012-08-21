(ns statisticator
  (:use (incanter core io stats charts)) )

(load "jmeter-loader-util")

(def ds (read-dataset-jmeter-file  "data/sample-1h-m1.jtl") )
(save ($rollup count :t :lb ds) "data/stats.csv")
(save (time-series-plot :ts :t :data ds) "data/ts.png") 