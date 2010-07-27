(ns koristiovo.main
  (:use [net.cgrand.enlive-html
         :only [deftemplate defsnippet content clone-for
                nth-of-type first-child do-> set-attr sniptest at emit*]])
  (:use compojure.core
        ring.adapter.jetty
        koristiovo.utils
        koristiovo.testdata)
  (:require (compojure [route :as route])
            (ring.util [response :as response])
            (koristiovo [core :as core]
                        [admin :as admin]
                        [middleware :as middleware]))
  (:gen-class))

;; ========================================
;; Routes
;; ========================================

(defroutes public-routes
           (GET "/" [] (render (core/index {:title "foo"})))
           (GET "/interview" [] (render (core/interview *sample-interview*))))

(defroutes admin-routes
           (GET "/login" [] (render (admin/login))))

(defroutes static-routes
           (GET ["/:filename" :filename #".*"] [filename]
                (response/file-response filename {:root "public"})))

(defroutes error-routes
           (route/not-found "Page not found"))

(wrap! public-routes (middleware/wrap-charset "utf8"))

(defroutes koristiovo-routes
           public-routes 
           admin-routes
           static-routes
           error-routes)

;; ========================================
;; The App
;; ========================================

(defn start-app []
  (future (run-jetty (var koristiovo-routes) {:port 8080})))

(defn -main [& args] (start-app))
