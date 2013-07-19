(ns cats-near-you.oauth
  (:require [clj-oauth2.client :as oauth]
            [cats-near-you.config :refer :all]))

(def gconf (config :glass))

(def google-oauth
  {:authorization-uri (:auth-uri gconf)
   :access-token-uri (:token-uri gconf)
   :redirect-uri (nth (:redirect gconf) 1)
   :client-id (:client-id gconf)
   :client-secret (:secret gconf)
   :access-query-param :access_token
   :scope ["https://www.googleapis.com/auth/glass.timeline"
           "https://www.googleapis.com/auth/userinfo.profile"]
   :grant-type "authorization_code"
   :access-type "offline"
   :approval_prompt :force})

(def authorization (atom nil))
(def req (atom nil))

(def auth-req
    (oauth/make-auth-request google-oauth))

(defn google-access-token
  ([]
   (reset! authorization
           (oauth/get-access-token google-oauth (:params req) auth-req)))
  ([request]
   (reset! req request)
   (oauth/get-access-token google-oauth (:params request) auth-req)))


