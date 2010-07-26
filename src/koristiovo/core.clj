(ns koristiovo.core
  (:use [net.cgrand.enlive-html
         :only [deftemplate defsnippet content clone-for
                nth-of-type first-child do-> set-attr sniptest at emit*]])
  (:use koristiovo.utils)
  (:use compojure)
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
   (GET "/"
      (render (index {:title "foo"})))
  (ANY "*"
       [404 "Page Not Found"]))


;; ========================================
;; The App
;; ========================================

(defonce *app* (atom nil))

(defn start-app []
  (if (not (nil? @*app*))
    (stop @*app*))
  (reset! *app* (run-server {:port 8080}
                            "/*" (servlet koristiovo-routes))))

(defn stop-app []
  (when @*app* (stop @*app*)))

