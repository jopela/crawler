(ns crawler.util-test
  (:require [clojure.test :refer :all]
            [crawler.util :refer :all]))

(deftest valid-url?-test
  (testing "Returns true if url is valid, false otherwise"
    (testing "valid url(s)"
      (are [i] (is (valid-url? i))
           "http://google.com"
           "http://en.wikipedia.org"
           "https://desjardins.org/secure"))
    (testing "invalid url(s)"
      (are [i] (is (not (valid-url? i)))
           nil
           "htt://invalid.org"
           "/relative/path"
           "ftp://domain.org"
           "mailto:someone@example.com?Subject=Hello"
           "http://hello world.com"))))

(deftest valid-uri?-test
  (testing "Returns true if uri is valid, false otherwise"
    (testing "Valud uri(s)"
      (are [i] (is (valid-uri? i))
           "http://google.com"
           "/some/path/to/resource"
           "."
           "../"))
    (testing "Invalid uri(s)"
      (are [i] (not (valid-uri? i))
           "hello world"
           "./ "
           ))))

(deftest absolute?-test
  (testing "Returns true if uri is absolute. False otherwise."
    (testing "Absolute uri"
      (are [i] (is (absolute? i))
           "http://en.wikipedia.org/wiki/Montreal"
           "http://google.com"))
    (testing "Relative uri"
      (are [i] (is (not (absolute? i)))
           "./"
           "/base/url"
           "."
           "../"
           ))))
           
