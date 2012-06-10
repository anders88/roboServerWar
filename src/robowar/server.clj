(ns robowar.server
  (:require [noir.server :as server])
  (:use [noir.core :only [defpage defpartial]])
)

(defn -main [& m]
  (let [mode (keyword (or (first m) :dev))
        port (Integer. (get (System/getenv) "PORT" "1337"))
        ]
    (server/start port {:mode mode
                        :ns 'robowar})))

(def counter (ref {:called 0}))

(defn num-visits []
  (let [newnum (inc (@counter :called))]
    (dosync (ref-set counter {:called newnum}))
    (str ":" newnum)
  )
  )

(defpage "/" []
  (let [newnum (inc (@counter :called))]
    (dosync (ref-set counter {:called newnum}))
    (str "I am here :" newnum)
  )
  )