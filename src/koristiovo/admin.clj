(ns koristiovo.admin
  "Administration interface"
  (:use [net.cgrand.enlive-html
         :only [deftemplate defsnippet html-resource content clone-for
                nth-of-type first-child do-> set-attr sniptest at emit*
                select]])
  (:use compojure.core
        koristiovo.utils
        koristiovo.users
        koristiovo.interview
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

(deftemplate interview-edit "koristiovo/templates/interview-edit.html"
             [data]
             [:#action] (content "Mijenjaj intervju"))

(deftemplate interview-add "koristiovo/templates/interview-edit.html"
             []
             [:#action] (content "Dodaj intervju"))

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

(defn do-list-interviews
      []
      (let [*list* (list-interviews)]
        (if (= [] *list*)
          (render (interview-list-empty))
          (render (interview-list *list*))))) 

;; ========================================
;; Tests
;; ========================================

