(ns perfdemo.core
  (:use (perfdemo report stats jmeterloader timestamp)))

(defn -main [& args]
  (let [ ds (time (read-dataset-jmeter-file  "resources/data/jmeter-sample.jtl"))
	model {:bar-url (time (generate-chart ds "bar-shops.png"))
	       :tsplot-url (time (generate-ts-plot ds "tsplot.png"))
	       :stats (time (group-stats-table ds))}  ]
    (time (save-page (build-page model)))
    (copy-file "resources/css/report.css" "output/css/report.css")
    ))

