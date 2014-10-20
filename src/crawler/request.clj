(ns crawler.request
 (:require [clj-http.client :as sync-http]
           [clojure.string :as s]
           [clojure.java.io :as io]
           [org.httpkit.client :as async-http]
           [clojure.core.async :refer [<! >! <!! >!! go chan close!]]))

(defn sync-get
  "Perform a synchronous get request. Body is a string or nil when not found"
  [url]
  (try
    (-> url 
        (sync-http/get {:as :text :throw-exceptions false})
        :body)
    (catch java.net.URISyntaxException e
      nil)
    (catch java.net.UnknownHostException e
      nil)
    (catch java.net.MalformedURLException e
      nil)
    (catch javax.net.ssl.SSLException e
      nil)
    (catch NullPointerException e
      nil)))

(defn async-get
  "Performs an asynchronous get request and puts the result on a channel."
  [ch url]
  (letfn [(callback [response] (if-let [err (:error response)] (go (>! ch err)) (go (>! ch (:body response)))))]
    (try
      (async-http/get url {:as :text} callback)
      (catch Exception e
        (go (>! ch e))))))

