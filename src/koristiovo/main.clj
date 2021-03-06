(ns koristiovo.main
  (:use [net.cgrand.enlive-html
         :only [deftemplate defsnippet html-resource content clone-for
                nth-of-type first-child do-> set-attr sniptest at emit*
                select]])
  (:use compojure.core
        clojure.contrib.duck-streams
        ring.adapter.jetty
        koristiovo.utils
        koristiovo.testdata
        koristiovo.interview
        sandbar.stateful-session)
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
           (GET "/login" [] (render (admin/login)))
           (POST "/login" [username password] (admin/do-login username password))
           (GET "/interview" [] (render (core/interview *sample-interview*))))

(defroutes admin-routes
           (GET "/add" [] (render (admin/interview-add)))
           (GET "/list" [] (admin/do-list-interviews))
           (GET "/edit/:id" [id] (render (admin/interview-edit (fetch-interview id)))))

(defroutes static-routes
           (GET ["/:filename" :filename #".*"] [filename]
                (response/file-response filename {:root "public"})))

(defroutes error-routes
           (route/not-found "Page not found"))

(wrap! public-routes (middleware/wrap-charset "utf8"))
(wrap! admin-routes (middleware/wrap-charset "utf8") middleware/wrap-admin)

(defroutes koristiovo-routes
           public-routes 
           admin-routes
           static-routes
           error-routes)

(wrap! koristiovo-routes wrap-stateful-session)

;; ========================================
;; The App
;; ========================================

(defn start-app []
  (future (run-jetty (var koristiovo-routes) {:port 8080})))

(defn -main [& args] (start-app))
