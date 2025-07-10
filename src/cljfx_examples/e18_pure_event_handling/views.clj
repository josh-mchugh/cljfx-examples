(ns cljfx-examples.e18-pure-event-handling.views
  (:require [cljfx.api :as fx]
            [cljfx-examples.e18-pure-event-handling.events :as events]
            [cljfx-examples.e18-pure-event-handling.subs :as subs]))

(defn- loading [_]
  {:fx/type :h-box
   :alignment :center
   :children [{:fx/type :progress-indicator}]})

(defn current-page [{:keys [fx/context]}]
  (case (:result (fx/sub-ctx context subs/current-response))
    :pending {:fx/type loading}
    nil {:fx/type :region}))

(defn toolbar [{:keys [fx/context]}]
  {:fx/type :h-box
   :spacing 10
   :children [{:fx/type :button
               :text "Back"
               :disable (fx/sub-ctx context subs/history-empty?)}
              {:fx/type :text-field
               :h-box/hgrow :always
               :text (fx/sub-val context :typed-url)
               :on-text-changed {:event/type ::events/type-url :fx/sync true}
               :on-key-pressed {:event/type ::events/key-press-url}}]})

(defn root [_]
  {:fx/type :stage
   :width 960
   :height 540
   :showing true
   :scene {:fx/type :scene
           :root {:fx/type :v-box
                  :padding 10
                  :spacing 10
                  :children [{:fx/type toolbar}
                             {:fx/type current-page
                              :v-box/vgrow :always}]}}})
