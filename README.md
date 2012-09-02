ClojureBigDataJugSummerCamp2012
==============================

The examples run on Clojure 1.3.0 and the last release of Incanter on Clojars, 1.3.0.

Unfortunately these is an issue in 1.3.0 regarding read-dataset (https://groups.google.com/forum/?fromgroups=#!topic/incanter/saIgmLdl8EQ).
Long values (e.g. the Java timestamp) are not parsed into Number.

The master branch https://github.com/liebke/incanter has been fixed but the jar hasn't been deployed.

I've rebuilt the module incanter-io by myself and patched the maven repo in order to be able to run the demos.

