(ns address-book.core.views.address-book-layout
  (:require [hiccup.page :refer [html5 include-css]]))

(defn common-layout [& body]
  (html5
    [:head
     [:title "Address book"]
     (include-css "/css/address-book.css")]

    [:body
     [:h1#content-title "Address-book"]
     body]))
