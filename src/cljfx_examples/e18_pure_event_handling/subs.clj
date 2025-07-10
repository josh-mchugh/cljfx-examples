(ns cljfx-examples.e18-pure-event-handling.subs
  (:require [cljfx.api :as fx]
            [clojure.string :as str]))

(defn current-request-id [context]
  (peek (fx/sub-val context :history)))

(defn response-by-request-id [context request-id]
  (fx/sub-val context get-in [:request-id->response request-id]))

(defn current-response [context]
  (let [id (fx/sub-ctx context current-request-id)]
    (fx/sub-ctx context response-by-request-id id)))

(defn history-empty? [context]
  (empty? (fx/sub-val context :history)))
