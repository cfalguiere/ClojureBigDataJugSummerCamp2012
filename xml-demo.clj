;; test xml

(ns statisticator
  (:require [clojure.xml :as xml]) )

;;(def datadir "../data/etalon/") ;;
;;(def datafilename "20120228-0933_MSALL_TPE2012IBM2.jtl") ;;  3 Mo -> 800
(def datadir "../data/6h/") ;;
(def datafilename "20120228-1100_MS1_TPS2012IBM2.jtl") ;; 87 Mo -> 8s
(def datafile (str datadir "/" datafilename))
(time (def xmldata (xml/parse datafile)))


