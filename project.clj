(defproject cats-near-you "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [clj-http "0.7.5"]
                 [ring/ring-core "1.2.0"]
                 [ring/ring-jetty-adapter "1.2.0"]
                 [compojure "1.1.5"]
                 [stuarth/clj-oauth2 "0.3.2"]
                 [com.cemerick/url "0.0.8"]
                 [org.clojure/data.json "0.2.2"]
                 [org.clojure/core.async "0.1.0-SNAPSHOT"]]
  :main cats-near-you.core
  :repositories {"sonatype-oss-public" "https://oss.sonatype.org/content/groups/public/"})
