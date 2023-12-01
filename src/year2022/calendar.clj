(ns year2022.calendar
  (:require
    [clojure.tools.namespace.repl :refer [refresh]]
    [year2022.day1.puzzles :as day-1]
    [year2022.day2.puzzles :as day-2]
    [year2022.day3.puzzles :as day-3]
    [year2022.day4.puzzles :as day-4]
    [year2022.day5.puzzles :as day-5]
    [year2022.day6.puzzles :as day-6]
    [year2022.day7.puzzles :as day-7]
    [year2022.day8.puzzles :as day-8]
    [year2022.day9.puzzles :as day-9]
    [year2022.day10.puzzles :as day-10]))

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
