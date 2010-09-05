(ns koristiovo.interview
    "Defines some basic I/O operations on interviews"
    (:use [net.cgrand.enlive-html
         :only [deftemplate defsnippet html-resource content clone-for
                nth-of-type first-child do-> set-attr sniptest at emit*
                select]])
    (:use koristiovo.utils
          clojure.test)
    (gen-class)) 

(defn list-interviews
  []
  (load-file "filelist.clj"))

(defn fetch-interview
  [id]
  (def source (html-resource (apply str (concat "koristiovo/templates/" id))))
  {:name (select source [:h1.name])
   :occupation (select source [:p.occupation])
   :image (:src (:attrs (first (select source [:#interview-image]))))
   :bio (select source [:#q-bio])
   :hardware (select source [:#q-hardware])
   :software (select source [:#q-software])
   :dream-setup (select source [:#q-dream-setup])
   :id id})
