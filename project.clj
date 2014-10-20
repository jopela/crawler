(defproject crawler "0.1.1"
  :description "sync and async web crawler"
  :license {:name "free liscence"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [enlive "1.1.5"]
                 [clj-http "1.0.0"]
                 [http-kit "2.1.16"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]]
  :main ^:skip-aot crawler.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
