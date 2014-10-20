(defproject crawler "0.1.1"
  :description "sync and async web crawler"
  :license {:name "free liscence"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [enlive "1.1.5"]
                 [clj-http "1.0.0"]]
  :main ^:skip-aot crawler.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
