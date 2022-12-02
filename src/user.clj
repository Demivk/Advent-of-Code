(ns user
  (:require
    [clojure.tools.namespace.repl :refer [refresh]]
    [day1.day1 :as day-1]
    [day2.day2 :as day-2]))

(def go refresh)

(comment
  go
  (day-1/part-1)
  (day-1/part-2)
  (day-2/part-1)
  (day-2/part-2))
