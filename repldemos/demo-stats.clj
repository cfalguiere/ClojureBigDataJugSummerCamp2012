(use '(incanter core stats))

(def ds (read-dataset  "readings.csv" :header true) )

;;; count entries
(nrow ds)

;;; compute mean
(mean ($ :t ds))

;;; compute many indicators
[ (count ($ :t ds)) (mean ($ :t ds)) (sd ($ :t ds)) ]
;; cleaner
(with-data ($ :t ds)
  [ (count $data) (mean $data) (sd $data) (quantile $data :probs[0.95]) ] ) 
;; insert labels
(with-data ($ :t ds)
  (zipmap [ "count" "mean" "sd" "q95"]
	  [ (count $data) (mean $data) (sd $data) (quantile $data :probs[0.95]) ] )) 

;;; groups
(distinct ($ :lb ds))
(keys ($group-by :lb ds))

;;; dataset for a group
(view (get ($group-by :lb ds) {:lb "SU01-HomePage-Page"} ))

;; number of readings per group
($rollup count :t :lb ds) ; yield a dataset

;; mean per group
($rollup mean :t :lb ds) ; yield a dataset

;;q95 per group
($rollup #(quantile % :probs[0.95])  :t :lb ds)



