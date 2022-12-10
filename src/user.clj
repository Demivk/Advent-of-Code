(ns user
  (:require
    [clojure.tools.namespace.repl :refer [refresh]]
    [day1.day1 :as day-1]
    [day2.day2 :as day-2]
    [day3.day3 :as day-3]
    [day4.day4 :as day-4]
    [day5.day5 :as day-5]
    [day6.day6 :as day-6]
    [day7.day7 :as day-7]
    [day8.day8 :as day-8]
    [day9.day9 :as day-9]
    [day10.day10 :as day-10]))

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
  (day-6/part-2)
  (day-7/part-1)                                            ; No idea how I managed to do day 7
  (day-7/part-2)
  (day-8/part-1)                                            ; Needed help with this one (in clojure), thanks Reddit
  (day-8/part-2)
  (day-9/part-1)
  (day-9/part-2)
  (day-10/part-1)
  (day-10/part-2))
