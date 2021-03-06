(ns crawler.href
  (:require [net.cgrand.enlive-html :as html]
            [clojure.java.io :as io]
            [crawler.util :as u]
            [clojure.string :as s]
            [clojure.core.reducers :as r]))

(defn parse
  "Parse an http response stream into an html 'dom' clojure data structure, returns an empty 'dom'
  when passed nil as argument."
  [s]
  (if (nil? s)
    '()
    (html/html-resource s)))

(defn anchors
  "Returns a collection of anchors html elem from a collection of tags."
  [coll]
  (html/select coll [:a]))

(defn page-href
  "Extract the href from the tag element."
  [elem]
  (get-in elem [:attrs :href]))

(defn normalize
  "Transform the uri into an absolute url anchored at base."
  [base uri]
  (if (and (u/valid-url? base) (u/valid-uri? uri))
    (if (u/absolute? uri)
      uri
      (let [parsed (java.net.URI. base)]
        (-> parsed
            (.resolve uri)
            .toString)))))

(defn remove-fragment
  [url]
  (if (and (not (nil? url)) (u/valid-url? url))
    (let [[no-fragment & more] (s/split url #"#")]
      no-fragment)))

(defn links
  "Returns a sequence of links from the page-rdr"
  [base page-rdr]
  (let [operations (comp (r/remove nil?)
                         (r/map remove-fragment)
                         (r/map (partial normalize base)) 
                         (r/map page-href))
        hrefs (anchors (parse page-rdr))]
    (into #{} (operations hrefs))))

