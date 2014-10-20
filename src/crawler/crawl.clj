(ns crawler.crawl
  (:require [clojure.java.io :as io]
            [crawler.href :as href]
            [crawler.request :as req]
            [org.httpkit.client :as async-http]
            [clojure.string :as s]))

(defn crawl-web-sync
  "Crawls the web starting from root. Filters the url found while crawling with the
  href-filter predicate. Crawls no more then limit unvisited urls. Returns a sequence of page
  html"
  [href-filter limit root]
  (loop [to-visit [root] visited #{} pages []]
    (if (or (empty? to-visit) (> (count visited) (dec limit)))
      pages
      (let [node (peek to-visit)]
        (if (visited node)
          (recur (pop to-visit) visited pages)
          (let [page-txt (req/sync-get node)]
            (recur (into (pop to-visit) (filter href-filter (href/links node (try (java.io.StringReader. page-txt)
                                                                                  (catch Exception e nil)))))
                   (conj visited node)
                   (conj pages page-txt))))))))

(defn crawl-web-async
  "Same as crawl-web-sync but using asynchronous programming"
  [href-filter limit root]
  (let [work-chan (chan)
        pages (atom [])
        visited (atom #{})]
    nil))

