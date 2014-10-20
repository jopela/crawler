(ns crawler.request-test
  (:require [clojure.test :refer :all]
            [crawler.request :refer :all]))

(deftest sync-get-test
  (testing "Return a stream of the content from url or nil when not found"
    (testing "invalid url"
      (are [i] (is (nil? (sync-get i)))
           "htp://google"
           "htttp://google.com"
           "http://en wikipedia.org"))))
