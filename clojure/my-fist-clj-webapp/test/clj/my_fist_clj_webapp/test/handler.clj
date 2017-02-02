(ns my-fist-clj-webapp.test.handler
  (:require [clojure.test :refer :all]
            [ring.mock.request :refer :all]
            [my-fist-clj-webapp.handler :refer :all]))

(deftest test-app
  (testing "main route"
    (let [response ((app) (request :get "/"))]
      (is (= 200 (:status response)))))

  (testing "not-found route"
    (let [response ((app) (request :get "/invalid"))]
      (is (= 404 (:status response))))))
