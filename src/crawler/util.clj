(ns crawler.util)

(defn valid-url?
  [url]
  (if-not (nil? url)
    (try
      (let [uri (java.net.URI. url)
            proto (-> url java.net.URL. .getProtocol)]
        (some #(= proto %) ["http" "https"]))
      (catch java.net.MalformedURLException e
        false)
      (catch java.net.URISyntaxException e
        false))))

(defn valid-uri?
  [uri]
  (if (nil? uri)
    false
    (try
      (let [u (java.net.URI. uri)]
        true)
      (catch java.net.URISyntaxException e
        false))))

(defn absolute?
  [uri]
  (-> uri java.net.URI. .isAbsolute))

