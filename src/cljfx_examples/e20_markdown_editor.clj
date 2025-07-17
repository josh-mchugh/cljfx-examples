(ns cljfx-examples.e20-markdown-editor
  (:require [cljfx.api :as fx]
            [clojure.string :as str]
            [clojure.core.cache :as cache])
  (:import [de.codecentric.centerdevice.javafxsvg SvgImageLoaderFactory]
           [de.codecentric.centerdevice.javafxsvg.dimension PrimitiveDimensionProvider]
           [java.awt Desktop]
           [java.io File]
           [java.net URI]
           [org.commonmark.node Node]
           [org.commonmark.parser Parser]))

(def *context
  (atom
   (fx/create-context {:typed-text (subs (slurp "README.md") 0 990)}
                      #(cache/lru-cache-factory % :threshold 4096))))

(defmulti handle-event :event/type)

(defmethod handle-event :default [e]
  (prn e))

(def app
  (fx/create-app *context
     :event-handler handle-event
     :desc-fn (fn [_]
                {:fx/type :stage
                 :showing true
                 :width 960
                 :height 540
                 :scene {:fx/type :scene
                         :stylesheets #{"markdown.css"}
                         :root {:fx/type :label
                                :text "Hello World!"}}})))
