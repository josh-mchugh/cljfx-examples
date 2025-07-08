(ns cljfx-examples.e18-pure-event-handling
  (:require [cljfx.api :as fx]
            [cljfx-examples.e18-pure-event-handling.views :as views]
            [clojure.core.cache :as cache]))

;; This is an example of simplistic web browser built on top of JavaFX: it can display
;; text, some images, and html as a tree of nodes. Events, subscriptions, and views are all
;; pure functions, and all mutations happen only in this ns

(def *state
  (atom
   (fx/create-context
    {:typed-url ""
     :request-id->response {}
     :history []}
    cache/lru-cache-factory)))


(def renderer
  (fx/create-renderer
   :middleware (comp
                fx/wrap-context-desc
                (fx/wrap-map-desc (fn [_] {:fx/type views/root})))
   :opts {:fx.opt/type->lifecycle #(or (fx/keyword->lifecycle %)
                                       (fx/fn->lifecycle-with-context %))}))

(fx/mount-renderer *state renderer)
