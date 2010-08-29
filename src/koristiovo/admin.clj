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

(deftemplate interview-list "koristiovo/templates/list.html"
             [file-list]
             [:ul#user-list :li] (clone-for [item file-list] 
                                            [:a] (do-> 
                                                   (content (:title item)) 
                                                   (set-attr :href (:file item)))))

(deftemplate interview-list-empty "koristiovo/templates/list.html" 
             []
             [:div#content] (content "Nema unosa u bazi"))

(deftemplate interview-edit "koristiovo/templates/interview-edit.html" [])

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

(defn list-interviews
  []
  (if (= (load-file "filelist.clj") [])
    (render (render (interview-list-empty)))
    (render (render (interview-list (load-file "filelist.clj"))))))

(defn edit-interview
  [id]
  (render (interview-edit)))

;; ========================================
;; Tests
;; ========================================

(deftest foo 
         (is (= true (check-login "username" "password" users-test)))
         (is (= false (check-login "foo" "bar" users-test))))
