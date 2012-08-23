(defproject hadoopwc "1.0.0-SNAPSHOT"
  :description "demo hadoop word count"
  :url "https://github.com/cfalguiere/ClojureBigDataJugSummerCamp2012p"
  :license {:name "Eclipse Public License 1.0"
            :url "http://opensource.org/licenses/eclipse-1.0.php"
            :distribution "repo"
            :comments "Same license as Clojure"}
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [org.apache.hadoop/hadoop-core "1.0.3"]
                 [clojure-hadoop/clojure-hadoop "1.4.1"]
                 [log4j/log4j "1.2.16" :exclusions [javax.mail/mail
                                                    javax.jms/jms
                                                    com.sun.jdmk/jmxtools
                                                    com.sun.jmx/jmxri]]
                 ]
  :dev-dependencies [[swank-clojure "1.4.2"]]
  :aot [clojure-hadoop.config
        clojure-hadoop.defjob
        clojure-hadoop.gen
        clojure-hadoop.imports
        clojure-hadoop.job
        clojure-hadoop.load
        clojure-hadoop.wrap
        clojure-hadoop.flow]
  :main hadoopwc.core)