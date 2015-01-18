(ns address-book.core.routes.address-book-routes
  (:require [compojure.core :refer [defroutes GET POST]]))

(defn example-post [request]
  (let [post-value (get-in request [:params :example-post])]
    (str "You posted: " post-value)))

(defroutes address-book-routes
  (GET  "/"     []  "Example GET")
  (POST "/post" []  example-post))
