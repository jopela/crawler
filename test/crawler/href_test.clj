(ns crawler.href-test
  (:require [clojure.test :refer :all]
            [crawler.href :refer :all]))

(deftest normalize-test
  (testing "Normalize uri into absolute url anchored at base."
    (testing "relatie  uri(s)"
      (are [base in out] (is (= out (normalize base in)))
           "http://en.wikipedia.org/wiki/Montreal" "../resource/Montreal" "http://en.wikipedia.org/resource/Montreal"
           "http://en.wikipedia.org/wiki/wiki/Montreal" "../../index.html" "http://en.wikipedia.org/index.html"
           "http://google.com/" "/resource/base/index.html" "http://google.com/resource/base/index.html"))
    (testing "absolute"
      (are [in] (is (= in (normalize "http://en.wikipeida.org/wiki/index.html" in)))
           "http://en.wikipedia.org/wiki/Montreal"
           "http://google.com"))
    (testing "invalid entries"
      (are [b u] (is (nil? (normalize b u)))
           nil nil
           "http://en.wikipedia.org/" nil
           nil "/relative/uri"
           "htp:/google" "/valid/relative"
           "http://google.com" "/inva lid"
           "aaa aa" "/inv alid"))))

(deftest remove-fragment-test
  (testing "Removes the fragment part from an url"
    (testing "valid input"
      (testing "fragment"
        (is (= "http://en.wikipedia.org/wiki/Montreal" (remove-fragment "http://en.wikipedia.org/wiki/Montreal#home"))))
      (testing "no fragment"
        (is (= "http://en.wiki.org" (remove-fragment "http://en.wiki.org")))))
    (testing "invalid input"
      (are [i] (is (nil? (remove-fragment i)))
           nil
           "asdasdasd #hahha"
           "http:/go.com"))))

