(ns koristiovo.admin
  "Administration interface"
  (:use [net.cgrand.enlive-html
         :only [deftemplate defsnippet content clone-for
                nth-of-type first-child do-> set-attr sniptest at emit*]])
  (:use compojure.core
        koristiovo.utils
        koristiovo.users
        sandbar.stateful-session
        clojure.test)
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

(defn check-login
  [username password user-list]
  (if (and (contains? user-list username) (= password (user-list username)))
    true
    false))

(defn do-login
  [username password]
  (if (check-login username password users)
    (do 
      (session-put! :user username)
      (response/redirect "/list"))
    (do 
      (response/redirect "/"))))

(deftest foo 
         (is (= true (check-login "username" "password" users-test)))
         (is (= false (check-login "foo" "bar" users-test))))
