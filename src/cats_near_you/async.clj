(ns cats-near-you.async
  (:require [cats-near-you.api :as api]
            [cats-near-you.oauth :as oauth])
  (:use clojure.core.async))

(defn push-cats [cats]
  (go
    (doseq [cat cats]
      (try (api/picture->glass cat)
        (catch Exception e
          (oauth/google-access-token)
          (api/picture->glass cat)))
      (Thread/sleep 600000))))

(defn start-cat-feed []
  (push-cats
    (map api/uri->picture (api/fetch-cat-uris))))
