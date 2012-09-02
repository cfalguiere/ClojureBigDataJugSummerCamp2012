(use '(incanter core io))

(def ds (read-dataset  "readings.csv" :header true) )

;;; how many lines ?
(nrow ds)

;;; view data
(view ds)
(head ds)

;;; select columns
(view ($ :lb ds))
(view ($ [:lb :t :shop] ds ))

(view ($order :t :desc ds))


;;; filtering values 
(view ($where {:s true} ds))

(distinct ($ :shop ds))
(view ($where {:shop "MAF"} ds))
(view ($ :t ($where {:shop "MAF"} ds)))
(with-data ds
	(view ($ :t ($where {:shop "MAF"}))))

;; queries with multiple criteria
(view ($where {:status true
	       :shop {:$in #{"MAF", "FAB"}}
	       :t {:$gt 3000 :$lt 5000}} ds))


