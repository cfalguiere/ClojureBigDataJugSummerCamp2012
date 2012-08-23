(ns incanterdemos.timestamp
  (:require [clojure.algo.generic.functor :as functor]
	    [incanter.core :as incanter]
	    [clj-time.coerce :as coerce]) )

;(defn timestamp [ds]
;    (conj-cols ds (col-names ($map #(coerce/from-long %) :ts ds )[:timestamp]) ))
;; workaround incanter bug on long conversion
(defn fix-timestamp [ds]
  (incanter/conj-cols ds (incanter/col-names
			  (incanter/$map #(Long/parseLong %) :ts ds )
			  [:tsn]) ))
(defn obj-timestamp [ds]
  (incanter/conj-cols ds (incanter/col-names
			  (incanter/$map #(coerce/from-long %) :tsn ds )
			  [:timestamp]) ))
