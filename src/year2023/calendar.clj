(ns year2023.calendar
  (:require
    [clojure.test :refer [testing is]]
    [clojure.tools.namespace.repl :refer [refresh]]
    [year2023.day1.puzzles :as day-1]))

(def go refresh)

(comment go)

(testing "Day 1 - part 1" (is (= (day-1/part-1) 55029)))
(testing "Day 1 - part 2" (is (= (day-1/part-2) 55686)))
