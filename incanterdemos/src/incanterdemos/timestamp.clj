(ns incanterdemos.timestamp
  (:require [clojure.algo.generic.functor :as functor]
	    [incanter.core :as incanter]
	    [clj-time.coerce :as coerce]) )

(defn readable-timestamp [ds]
  (incanter/conj-cols ds (incanter/col-names
			  (incanter/$map #(coerce/from-long %) :ts ds )
			  [:timestamp]) ))
