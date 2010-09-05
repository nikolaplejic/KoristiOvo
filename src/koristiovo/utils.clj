(ns koristiovo.utils
  (:require [net.cgrand.enlive-html :as html]
            [clojure.contrib.error-kit :as kit])
  (:use clojure.test))

(defn render [t]
  (apply str t))

;; ========================================
;; Error checking
;; ========================================

(kit/deferror *required-field* [] [field]
              "Indicates encouter of an empty required field"
              {:msg (str "Empty field: " field)
               :unhandled (kit/throw-msg Exception)}) 

(defn check-empty
  [field value]
  (if (= value "")
    (kit/raise *required-field* field)
    true))

;; ========================================
;; Tests
;; ========================================

(deftest throws-exception-on-empty-field
         (is (thrown-with-msg? Exception (re-pattern "Empty field\\: field") (check-empty "field" "")))) 

(deftest does-not-throw-exception-on-non-empty-field
         (is (= true (check-empty "field" "value")))) 
