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

(defroutes public-routes
           (GET "/" [] (render (index {:title "foo"}))))

(defroutes static-routes
           (GET ["/:filename" :filename #".*"] [filename]
                (response/file-response filename {:root "public"})))

(defroutes error-routes
           (route/not-found "Page not found"))

(defroutes koristiovo-routes
           public-routes 
           static-routes
           error-routes)

;; ========================================
;; The App
;; ========================================

(defn start-app []
  (run-jetty (var koristiovo-routes) {:port 8080}))
