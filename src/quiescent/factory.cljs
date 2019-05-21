(ns quiescent.factory
  (:require-macros [quiescent.factory :as f])
  (:require [cljsjs.react]
            [goog.object :as gobj]))

(defn create-react-class [display-name impl]
  (let [cmp (fn [props context updater]
              (cljs.core/this-as this
                (js/React.Component.call this props context updater)
                this))]
    (gobj/extend (.-prototype cmp) js/React.Component.prototype (clj->js impl))

    (when display-name
      (set! (.-displayName cmp) display-name)
      (set! (.-cljs$lang$ctorStr cmp) display-name)
      (set! (.-cljs$lang$ctorPrWriter cmp)
            (fn [this writer opt]
              (cljs.core/-write writer display-name))))
    (set! (.-cljs$lang$type cmp) true)
    (set! (.. cmp -prototype -constructor) cmp)))

(defn factory
  "Return a Component factory function. The argument may be any
   value accepted by React.createElement (that is, the string name of a
   HTML tag, or an instance of ReactClass).

   Returns a function that takes props and children (the same as the
   built-in ReactJS element constructors)."
  [type]
  (fn [props & children]
      (apply js/React.createElement type (clj->js props) children)))
