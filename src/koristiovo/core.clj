(ns koristiovo.core
  (:use [net.cgrand.enlive-html
         :only [deftemplate defsnippet content clone-for
                nth-of-type first-child do-> set-attr sniptest at emit*]])
  (:use compojure.core
        ring.adapter.jetty
        koristiovo.utils)
  (:require (compojure [route :as route])
            (ring.util [response :as response]))
  (:gen-class))

;; ========================================
;; Templates
;; ========================================

(deftemplate index "koristiovo/index.html"
             [ctxt]
             [:title] (content (:title ctxt)))

;; ========================================
;; Routes
;; ========================================

(defroutes koristiovo-routes
           (GET "/" []
                (render (index {:title "foo"})))
           (GET ["/:filename" :filename #".*"] [filename]
                (response/file-response filename {:root "public"}))
           (route/not-found "Page not found"))

;; ========================================
;; The App
;; ========================================

(defonce *app* (atom nil))

(defn start-app []
  (run-jetty koristiovo-routes {:port 8080}))
