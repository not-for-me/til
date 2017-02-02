(ns my-fist-clj-webapp.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[my-fist-clj-webapp started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[my-fist-clj-webapp has shut down successfully]=-"))
   :middleware identity})
