(ns demomongo
  (:require [somnium.congomongo :as congomongo]
	    [incanter.core :as incanter]
	    [incanter.io :as io]
	    [incanter.mongodb :as mongodb]))

(defn load-data [filename]
  (io/read-dataset filename :header true) )

(defn batch-insert-dataset [ds]
  (let [conn (congomongo/make-connection :demodb)]
    (congomongo/with-mongo conn
      (mongodb/insert-dataset :readings ds))))

(defn lookup-dataset []
  (let [conn (congomongo/make-connection :demodb)]
    (congomongo/with-mongo conn
      (mongodb/fetch-dataset :readings ))))

(defn -main [& args]
  (let [ds ( load-data  "../data/readings.csv")]
    (batch-insert-dataset ds)
    (let [ds2 (lookup-dataset)]
      (println (incanter/nrow ds2 ))))


