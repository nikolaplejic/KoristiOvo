(ns koristiovo.middleware
  "Misc. middleware for the app"
  (:use sandbar.stateful-session))

;; UTF-8 hack for Compojure 0.4.0
;; http://groups.google.com/group/compojure/msg/6f572dc2459ee337
(defn wrap-charset [handler charset] 
  (fn [request] 
    (if-let [response (handler request)] 
      (if-let [content-type (get-in response [:headers "Content-Type"])] 
        (if (.contains content-type "charset") 
          response 
          (assoc-in response 
                    [:headers "Content-Type"] 
                    (str content-type "; charset=" charset))) 
        response)))) 

(defn wrap-admin 
  "If the user is logged in, let her in. Otherwise, 404."
  [handler]
  (fn [request]
    (if-let [user (session-get :user)]
      (handler request))))
