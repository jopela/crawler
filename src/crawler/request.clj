(ns crawler.request
 (:require [clj-http.client :as sync-http]
           [clojure.string :as s]
           [clojure.java.io :as io]))

(defn sync-get
  "Perform a synchronous get request. Body is a stream or nil when not found"
  [url]
  (try
    (-> url 
        (sync-http/get {:as :stream :throw-exceptions false})
        :body)
    (catch java.net.URISyntaxException e
      nil)
    (catch java.net.UnknownHostException e
      nil)
    (catch java.net.MalformedURLException e
      nil)
    (catch NullPointerException e
      nil)))

(defn stream->rdr
  "Reads the content of an InputStream into a reader"
  [stream]
  (io/reader stream))

