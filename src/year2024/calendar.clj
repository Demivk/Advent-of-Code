(ns year2024.calendar
  (:require
    [clojure.test :refer [testing is]]
    [clojure.tools.namespace.repl :refer [refresh]]
    [year2024.day01.puzzles :as day-01]
    [year2024.day02.puzzles :as day-02]
    [year2024.day03.puzzles :as day-03]
    [year2024.day04.puzzles :as day-04]
    [year2024.day05.puzzles :as day-05]
    [year2024.day06.puzzles :as day-06]
    [year2024.day07.puzzles :as day-07]
    [year2024.day08.puzzles :as day-08]
    [year2024.day09.puzzles :as day-09]
    [year2024.day10.puzzles :as day-10]
    [year2024.day11.puzzles :as day-11]
    [year2024.day12.puzzles :as day-12]
    [year2024.day13.puzzles :as day-13]
    [year2024.day14.puzzles :as day-14]
    [year2024.day15.puzzles :as day-15]))

(def go refresh)

(comment go)

(defn run-timed-tests []
  (println "✨ 2024 ✨")
  (time
    (do
      (println "🎄Day 1")
      (time (testing "Day 1 - part 1" (is (= (day-01/part-1) 3574690))))
      (time (testing "Day 1 - part 2" (is (= (day-01/part-2) 22565391))))

      (println "\n🎄Day 2")
      (time (testing "Day 2 - part 1" (is (= (day-02/part-1) 680))))
      (time (testing "Day 2 - part 2" (is (= (day-02/part-2) 710))))

      (println "\n🎄Day 3")
      (time (testing "Day 3 - part 1" (is (= (day-03/part-1) 169021493))))
      (time (testing "Day 3 - part 2" (is (= (day-03/part-2) 111762583))))

      (println "\n🎄Day 4")
      (time (testing "Day 4 - part 1" (is (= (day-04/part-1) 2370))))
      (time (testing "Day 4 - part 2" (is (= (day-04/part-2) 1908))))

      (println "\n🎄Day 5")
      (time (testing "Day 5 - part 1" (is (= (day-05/part-1) 6498))))
      (time (testing "Day 5 - part 2" (is (= (day-05/part-2) 5017))))

      (println "\n🎄Day 6")
      (time (testing "Day 6 - part 1" (is (= (day-06/part-1) 5067))))
      (time (testing "Day 6 - part 2" (is (= (day-06/part-2) 1793))))

      (println "\n🎄Day 7")
      (time (testing "Day 7 - part 1" (is (= (day-07/part-1) 5512534574980))))
      (time (testing "Day 7 - part 2" (is (= (day-07/part-2) 328790210468594))))

      (println "\n🎄Day 8")
      (time (testing "Day 8 - part 1" (is (= (day-08/part-1) 299))))
      (time (testing "Day 8 - part 2" (is (= (day-08/part-2) 1032))))

      (println "\n🎄Day 9")
      (time (testing "Day 9 - part 1" (is (= (day-09/part-1) 6386640365805))))
      (time (testing "Day 9 - part 2" (is (= (day-09/part-2) 0))))

      (println "\n🎄Day 10")
      (time (testing "Day 10 - part 1" (is (= (day-10/part-1) 754))))
      (time (testing "Day 10 - part 2" (is (= (day-10/part-2) 1609))))

      (println "\n🎄Day 11")
      (time (testing "Day 11 - part 1" (is (= (day-11/part-1) 231278))))
      (time (testing "Day 11 - part 2" (is (= (day-11/part-2) 274229228071551))))

      (println "\n🎄Day 12")
      (time (testing "Day 12 - part 1" (is (= (day-12/part-1) 1433460))))
      (time (testing "Day 12 - part 2" (is (= (day-12/part-2) 0))))

      (println "\n🎄Day 13")
      (time (testing "Day 13 - part 1" (is (= (day-13/part-1) 39748))))
      (time (testing "Day 13 - part 2" (is (= (day-13/part-2) 74478585072604))))

      (println "\n🎄Day 14")
      (time (testing "Day 14 - part 1" (is (= (day-14/part-1) 225810288))))
      (time (testing "Day 14 - part 2" (is (= (day-14/part-2) 6752))))

      (println "\n🎄Day 15")
      (time (testing "Day 15 - part 1" (is (= (day-15/part-1) 1526673))))
      (time (testing "Day 15 - part 2" (is (= (day-15/part-2) 0))))

      (println "\n🎅🏻Total time:"))))

(comment (run-timed-tests))
