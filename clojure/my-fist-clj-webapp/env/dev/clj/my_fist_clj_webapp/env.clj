(ns my-fist-clj-webapp.env
  (:require [selmer.parser :as parser]
            [clojure.tools.logging :as log]
            [my-fist-clj-webapp.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[my-fist-clj-webapp started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[my-fist-clj-webapp has shut down successfully]=-"))
   :middleware wrap-dev})
