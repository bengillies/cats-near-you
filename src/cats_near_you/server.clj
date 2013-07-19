(ns cats-near-you.server
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [compojure.handler :as handler]
            [cats-near-you.oauth :as oauth]
            [cats-near-you.async :as async])
  (:use [ring.util.response :only [redirect]]))

(defroutes main-routes
  (GET "/" [] (redirect (:uri oauth/auth-req)))
  (GET "/glasscallback" params
       (let [access-token (oauth/google-access-token params)]
         (reset! oauth/authorization access-token)
         (async/start-cat-feed)
         (str "Commencing cats!"))))

(def app
  (handler/site main-routes))
