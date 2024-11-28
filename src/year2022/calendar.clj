(ns year2022.calendar
  (:require
    [clojure.tools.namespace.repl :refer [refresh]]
    [year2022.day01.puzzles :as day-01]
    [year2022.day02.puzzles :as day-02]
    [year2022.day03.puzzles :as day-03]
    [year2022.day04.puzzles :as day-04]
    [year2022.day05.puzzles :as day-05]
    [year2022.day06.puzzles :as day-06]
    [year2022.day07.puzzles :as day-07]
    [year2022.day08.puzzles :as day-08]
    [year2022.day09.puzzles :as day-09]
    [year2022.day10.puzzles :as day-10]))

(def go refresh)

(comment
  go
  (day-01/part-1)
  (day-01/part-2)
  (day-02/part-1)
  (day-02/part-2)
  (day-03/part-1)
  (day-03/part-2)
  (day-04/part-1)
  (day-04/part-2)
  (day-05/part-1)
  (day-05/part-2)
  (day-06/part-1)
  (day-06/part-2)
  (day-07/part-1)                                           ; No idea how I managed to do day 7
  (day-07/part-2)
  (day-08/part-1)                                           ; Needed help with this one (in clojure), thanks Reddit
  (day-08/part-2)
  (day-09/part-1)
  (day-09/part-2)
  (day-10/part-1)
  (day-10/part-2))
