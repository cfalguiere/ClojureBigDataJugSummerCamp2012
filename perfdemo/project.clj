(defproject perfdemo "1.0.0-SNAPSHOT"
  :description "hiccup + stats + xmlloader +save incanter demo"
  :url "https://github.com/cfalguiere/ClojureBigDataJugSummerCamp2012p"
  :license {:name "Eclipse Public License 1.0"
            :url "http://opensource.org/licenses/eclipse-1.0.php"
            :distribution "repo"
            :comments "Same license as Clojure"}
  :dependencies [[org.clojure/clojure "1.3.0"]
		 [incanter "1.3.0"]
		 [org.clojure/algo.generic "0.1.0"]
		 [hiccup "1.0.1"]
		 [org.clojure/algo.generic "0.1.0"]
		 [clj-time "0.4.4"]]
  :dev-dependencies [[swank-clojure "1.4.2"]]
  :main perfdemo.core
  :jvm-opts ["-Xmx1g"] )



