(ns cljfx-examples.e18-pure-event-handling.subs
  (:require [cljfx.api :as fx]
            [clojure.string :as str]))

(defn history-empty? [context]
  (empty? (fx/sub-val context :history)))
