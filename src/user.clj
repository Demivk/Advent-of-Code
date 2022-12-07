(ns user
  (:require
    [clojure.tools.namespace.repl :refer [refresh]]
    [day1.day1 :as day-1]
    [day2.day2 :as day-2]
    [day3.day3 :as day-3]
    [day4.day4 :as day-4]
    [day5.day5 :as day-5]
    [day6.day6 :as day-6]))

(def go refresh)

(comment
  go
  (day-1/part-1)
  (day-1/part-2)
  (day-2/part-1)
  (day-2/part-2)
  (day-3/part-1)
  (day-3/part-2)
  (day-4/part-1)
  (day-4/part-2)
  (day-5/part-1)
  (day-5/part-2)
  (day-6/part-1)
  (day-6/part-2))
