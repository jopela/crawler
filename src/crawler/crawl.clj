(ns crawler.crawl
  (:require [crawler.href :as href]
            [crawler.request :as request]
            [crawler.string :as s]))

(defn crawl-web
  "Crawls the web starting from root. Filters the url found while crawling with the
  href-filter predicate. Crawls no more then limit unvisited urls. Returns a sequence of page
  html"
  [href-filter limit root]
  (loop [to-visit [root] visited #{} pages []]
    (if (empty? to-visit)
      pages
      (let [node (peek to-visit)]
        (if (visited node)
          (recur (pop to-visit) visited pages)
          (let [page-rdr (-> node sync-get stream->rdr)]
            (recur (concat (pop to-visit) (href/links node parse-rdr)) 
                   (conj visited node)
                   (conj pages (s/join "/n" (line-seq page-rdr))))))))))
