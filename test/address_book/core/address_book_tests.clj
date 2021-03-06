(ns address-book.core.address-book-tests
  (:use midje.sweet)
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [address-book.core.handler :refer :all]
            [address-book.core.models.query-defs :as query]))

(facts "Example GET and POST tests"
       (with-state-changes [(before :facts (query/create-contacts-table-if-not-exists!))
                            (after :facts (query/drop-contacts-table!))]

       (fact "Test GET"
             (query/insert-contact<! {:name "A"
                                      :phone "1"
                                      :email "a@x.com"})
             (query/insert-contact<! {:name "B"
                                      :phone "2"
                                      :email "b@x.com"})
             (let [response (app (mock/request :get "/"))]
               (:status response) => 200
               (:body response) => (contains "<div class=\"column-1\">A</div>")
               (:body response) => (contains "<div class=\"column-1\">B</div>")))

       (fact "Test POST"
             (count (query/all-contacts)) => 0
             (let [response (app (mock/request :post "/post" 
                                               {:name "D" 
                                                :phone "4" 
                                                :email "d@x.com"}))]
               (:status response) => 302
               (count (query/all-contacts)) => 1))

       (fact "Test update"
             (query/insert-contact<! {:name "_A"
                                       :phone "0"
                                       :email "a@x.com"})
             (let [response (app (mock/request :post "/edit/1"
                                               {:id "1"
                                                :name "A"
                                                :phone "1"
                                                :email "a@x.com"}))]
               (:status response) => 302
               (count (query/all-contacts)) => 1
               (first (query/all-contacts)) => {:id 1
                                                :name "A"
                                                :phone "1"
                                                :email "a@x.com"}))

       (fact "Test delete"
             (query/insert-contact<! {:name "A"
                                       :phone "1"
                                       :email "a@x.com"})
             (query/insert-contact<! {:name "B"
                                       :phone "2"
                                       :email "b@x.com"})
             (count (query/all-contacts)) => 2
             (let [response (app (mock/request :post "/delete/1"))]
               (:status response) => 302
               (count (query/all-contacts)) => 1
               (first (query/all-contacts)) => {:id 2
                                                :name "B"
                                                :phone "2"
                                                :email "b@x.com"}))))
