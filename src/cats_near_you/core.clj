(ns cats-near-you.core
  (:require [cats-near-you.server :as server])
  (:use [cats-near-you.config :only [config]]
        [ring.adapter.jetty :only [run-jetty]]))

(defn -main []
  (run-jetty server/app (:server config)))
