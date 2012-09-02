(use '(incanter core  charts))

(def ds (read-dataset  "readings.csv" :header true) )

;;; time series of resp time
(view (time-series-plot :ts :t :data ds))

(doto  (time-series-plot :ts :t :data ds :title "Readings"
			 :x-label "time" :y-label "resp. time (ms)"
			 :legend true :series-label "duration ms"
			 )
    (set-stroke-color java.awt.Color/blue)
    view)

;;; histogram of resp time
(view (histogram :t :data ds) )


;;; bar-chart count per shop
(view (bar-chart :shop :t :vertical false
		 :data  ($rollup count :t :shop ds)))

;;; box-plot 
(view (box-plot :t :group-by :shop :data ds) :legend true)

(def groups ($group-by :shop ds))
(doto (box-plot :t 
        :data (get groups {:shop "MAF"}) 
        :legend true :series-label "MAF")  
      (add-box-plot :t 
        :data (get groups {:shop "OIN"}) 
        :series-label "OIN")
      (add-box-plot :t 
        :data (get groups {:shop "WDK"}) 
        :series-label "WDK")
      (add-box-plot :t 
        :data (get groups {:shop "FAB"}) 
        :series-label "FAB")
      view)

;;; complex plot

(def ts (sel ds :cols :tsn))
(doto  (time-series-plot :ts :t :data ds :title "Readings"
			 :x-label "time" :y-label "resp. time (ms)"
			 :legend true :series-label "duration ms"
			 )
  (add-lines  ts (repeat 3000) :series-label "threshold")
  (set-stroke-color java.awt.Color/blue :series 0) 
  (set-stroke :width 1 :series 0) 
  (set-stroke-color java.awt.Color/red :series 1) 
  (set-stroke :width 3 :series 1) 
    view)

