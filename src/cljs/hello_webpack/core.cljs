(ns hello-webpack.core
  (:require [goog.dom :as gdom]
            [reagent.core :as r]
            [luggage]))

(set! *warn-on-infer* true)
(enable-console-print!)

(defonce recipes-state (r/atom nil))
(defonce api-key "w7gUxoguV8wAAAAAAAAS4LMofT0rK6AKLJKFauRa7oQWjx1GPA9RzHnjxPqUhNxH")

(def ^js/Luggage.collection recipes
  (-> api-key
      (luggage/DropboxBackend.)
      (luggage/Luggage.)
      (.collection "recipes")))

(defn fetch-recipes []
  (-> recipes
      (.read)
      ^js/Promise (.then #(reset! recipes-state %))))

(defn simple-component []
  [:ul
   (for [recipe @recipes-state]
     [:li {:key (aget recipe "slug")}
      (aget recipe "title")])])

(defn start []
  (fetch-recipes)
  (r/render [simple-component] (gdom/getElement "app")))

(start)

