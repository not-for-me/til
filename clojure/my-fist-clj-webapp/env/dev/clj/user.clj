(ns user
  (:require [mount.core :as mount]
            my-fist-clj-webapp.core))

(defn start []
  (mount/start-without #'my-fist-clj-webapp.core/http-server
                       #'my-fist-clj-webapp.core/repl-server))

(defn stop []
  (mount/stop-except #'my-fist-clj-webapp.core/http-server
                     #'my-fist-clj-webapp.core/repl-server))

(defn restart []
  (stop)
  (start))


