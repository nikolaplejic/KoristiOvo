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

