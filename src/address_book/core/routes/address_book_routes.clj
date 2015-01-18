(ns address-book.core.routes.address-book-routes
  (:require [compojure.core :refer :all]
            [ring.util.response :as response]
            [address-book.core.views.address-book-layout :refer 
             [common-layout 
              read-contact
              add-contact-form]]))

(def contacts (atom [{:id 1 :name "A" :phone "1" :email "a@x.com"}
                     {:id 2 :name "B" :phone "2" :email "b@x.com"}
                     {:id 3 :name "C" :phone "3" :email "c@x.com"}]))

(defn next-id []
  (->>
    @contacts
    (map :id)
    (apply max)
    (+ 1)))

(defn post-route [request]
  (let [name (get-in request [:params :name])
        phone (get-in request [:params :phone])
        email (get-in request [:params :email])]
    (swap! contacts conj {:id (next-id) :name name :phone phone :email email})
    (response/redirect "/")))

(defn get-route [request]
  (common-layout
    (for [contact @contacts]
      (read-contact contact))
    (add-contact-form)))

(defroutes address-book-routes
  (GET  "/"     []  get-route)
  (POST "/post" []  post-route))
