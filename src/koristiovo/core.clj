(ns koristiovo.core
  (:use [net.cgrand.enlive-html
         :only [deftemplate defsnippet content clone-for
                nth-of-type first-child do-> set-attr sniptest at emit*]])
  (:use compojure.core
        koristiovo.utils)
  (:gen-class))

;; ========================================
;; Templates
;; ========================================

(deftemplate index "koristiovo/templates/index.html"
             [ctxt]
             [:title] (content (:title ctxt)))

(deftemplate interview "koristiovo/templates/interview.html"
             [ctxt]
             [:title] (content (:name ctxt) " koristi ovo:")
             [:h1.name] (content (:name ctxt))
             [:p.occupation] (content (:occupation ctxt))
             [:p#q-software] (content (:software ctxt))
             [:p#q-hardware] (content (:hardware ctxt))
             [:p#q-dream-setup] (content (:dream-setup ctxt))
             [:p#q-bio] (content (:bio ctxt))
             [:img#interview-image] (set-attr :src (:image ctxt)))

