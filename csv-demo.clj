;; test csv

(ns statisticator
  (:use (incanter core io stats)))


(def inputds 
  (read-dataset "sample-file.csv" :header true))

(mean ($ :t inputds))

