(ns cljfx-examples.e18-pure-event-handling.events
  (:require [cljfx.api :as fx]
            [cljfx-examples.e18-pure-event-handling.subs :as subs])
  (:import [java.util UUID]
           [javafx.scene.input KeyCode KeyEvent]))

(defmulti event-handler :event/type)

(defmethod event-handler :default [event]
  (prn event))

(defmethod event-handler ::open-url [{:keys [fx/context url]}]
  (let [request-id (UUID/randomUUID)]
    {:context (fx/swap-context
               context
               (fn [m]
                 (-> m
                     (assoc :typed-url url)
                     (assoc-in [:request-id->response request-id] {:result :pending :url url})
                     (update :history conj request-id))))
     :http {:method :get
            :url url
            :on-response {:event/type ::on-response
                          :request-id request-id
                          :result :success}
            :on-exception {:event/type ::on-response
                           :request-id request-id
                           :result :failure}}}))
