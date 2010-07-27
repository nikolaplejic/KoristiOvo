(defproject koristiovo "0.1.0"
  :description "Enlive Tutorial"
  :dependencies [[org.clojure/clojure "1.1.0"]
                 [org.clojure/clojure-contrib "1.1.0"]
                 [compojure "0.4.1"]
                 [enlive "1.0.0-SNAPSHOT"]
                 [ring/ring-jetty-adapter "0.2.5"]
                 [ring/ring-devel "0.2.5"]
                 [sandbar/sandbar "0.2.4"]]
  :dev-dependencies [[leiningen/lein-swank "1.1.0"]]
  :source-path "src"
  :main koristiovo.main)
