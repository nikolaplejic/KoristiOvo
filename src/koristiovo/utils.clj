(ns koristiovo.utils
  (:require [net.cgrand.enlive-html :as html]))

(defn render [t]
  (apply str t))
