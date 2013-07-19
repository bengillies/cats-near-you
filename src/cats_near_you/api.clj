(ns cats-near-you.api
  (:require [clj-http.client :as http]
            [clojure.data.json :as json]
            [clojure.string :as string]
            [cats-near-you.oauth :as oauth])
  (:use [cats-near-you.config :only [config]]
        [cemerick.url :only [url-encode]]))

(defn build-flickr-search-uri [q]
  (string/join "&" ["http://api.flickr.com/services/rest/?method=flickr.photos.search"
                    (str "api_key=" (-> config :flickr :key))
                    (str "text=" (url-encode q))
                    "format=json"
                    "nojsoncallback=1"]))

(defn build-flickr-cat-uri [cat]
  (str "http://farm"
       (cat "farm")
       ".staticflickr.com/"
       (cat "server") "/"
       (cat "id") "_" (cat "secret")
       ".jpg"))

(defn fetch-cat-uris []
  (let [flickr-uri (build-flickr-search-uri "cat")
        resp (http/get flickr-uri)]
    (when (= (:status resp) 200)
      (map
        build-flickr-cat-uri
        (take 10
              (->
                (json/read-str (:body resp))
                (get "photos")
                (get "photo")))))))

(defn uri->picture [uri]
  (:body (http/get uri {:as :byte-array})))

(defn hash->byte-array [a]
  (.getBytes (json/write-str a)))

(defn picture->glass [picture]
  (http/post "https://www.googleapis.com/upload/mirror/v1/timeline"
             {:headers {"Authorization"
                        (str "Bearer "
                             (:access-token (deref oauth/authorization)))}
              :throw-entire-message? true
              :multipart [{:name "message"
                           :mime-type "application/json"
                           :content (hash->byte-array {:text "meow"})}
                          {:name "cat"
                           :content picture
                           :mime-type "image/jpeg"}]})
  (println "Cat sent!"))

