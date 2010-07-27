(ns koristiovo.admin
  "Administration interface"
  (:use [net.cgrand.enlive-html
         :only [deftemplate defsnippet content clone-for
                nth-of-type first-child do-> set-attr sniptest at emit*]])
  (:use compojure.core
        koristiovo.utils)
  (:gen-class))

;; ========================================
;; Templates
;; ========================================

(deftemplate index "koristiovo/templates/admin.html"
             [ctxt]
             [:title] (content (:title ctxt)))

(deftemplate login "koristiovo/templates/login.html" [])

;; ========================================
;; Misc. methods
;; ========================================

;;(defn do-login
;;  [username password]
;;  (if (and (= username "nikola") (= password "np2010"))
;;    (session/session-put! :user username :password password)
;;    nil))
