(defproject repldemos "1.0.0-SNAPSHOT"
  :description "demos incanter in lein"
  :url "https://github.com/cfalguiere/ClojureBigDataJugSummerCamp2012p"
  :license {:name "Eclipse Public License 1.0"
            :url "http://opensource.org/licenses/eclipse-1.0.php"
            :distribution "repo"
            :comments "Same license as Clojure"}
  :dependencies [[org.clojure/clojure "1.3.0"]
		 [incanter "1.3.0"]
		 [org.clojure/algo.generic "0.1.0"]]
  :dev-dependencies [[swank-clojure "1.4.2"]]
  :main loaderdemo.jmeterloader)
