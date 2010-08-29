(ns koristiovo.admin
  "Administration interface"
  (:use [net.cgrand.enlive-html
         :only [deftemplate defsnippet content clone-for
                nth-of-type first-child do-> set-attr sniptest at emit*]])
  (:use compojure.core
        koristiovo.utils
        koristiovo.users
        sandbar.stateful-session)
  (:require (sandbar [stateful-session :as session])
     (ring.util [response :as response]))
  (:gen-class))

;; ========================================
;; Templates
;; ========================================

(deftemplate index "koristiovo/templates/admin.html"
             [ctxt]
             [:title] (content (:title ctxt)))

(deftemplate login "koristiovo/templates/login.html" [])

(deftemplate interview-list "koristiovo/templates/list.html" [])

;; ========================================
;; Misc. methods
;; ========================================

(defn do-login
  [username password]
  (if (and (contains? users username) (= password (users username)))
    (do 
      (session-put! :user username)
      (response/redirect "/list"))
    (do 
      (response/redirect "/"))))
