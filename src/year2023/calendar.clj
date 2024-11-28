(ns year2023.calendar
  (:require
    [clojure.test :refer [testing is]]
    [clojure.tools.namespace.repl :refer [refresh]]
    [year2023.day01.puzzles :as day-01]
    [year2023.day02.puzzles :as day-02]
    [year2023.day03.puzzles :as day-03]
    [year2023.day04.puzzles :as day-04]
    [year2023.day05.puzzles :as day-05]
    [year2023.day06.puzzles :as day-06]
    [year2023.day07.puzzles :as day-07]
    [year2023.day08.puzzles :as day-08]
    [year2023.day09.puzzles :as day-09]
    [year2023.day10.puzzles :as day-10]
    [year2023.day11.puzzles :as day-11]
    [year2023.day12.puzzles :as day-12]
    [year2023.day13.puzzles :as day-13]
    [year2023.day14.puzzles :as day-14]
    [year2023.day15.puzzles :as day-15]))

(def go refresh)

(comment go)

(defn run-timed-tests []
  (println "✨ 2023 ✨")
  (time
    (do
      (println "🎄Day 1")
      (time (testing "Day 1 - part 1" (is (= (day-01/part-1) 55029))))
      (time (testing "Day 1 - part 2" (is (= (day-01/part-2) 55686))))

      (println "\n🎄Day 2")
      (time (testing "Day 2 - part 1" (is (= (day-02/part-1) 2331))))
      (time (testing "Day 2 - part 2" (is (= (day-02/part-2) 71585))))

      (println "\n🎄Day 3")
      (time (testing "Day 3 - part 1" (is (= (day-03/part-1) 528819))))
      (time (testing "Day 3 - part 2" (is (= (day-03/part-2) 80403602))))

      (println "\n🎄Day 4")
      (time (testing "Day 4 - part 1" (is (= (day-04/part-1) 21821))))
      (time (testing "Day 4 - part 2" (is (= (day-04/part-2) 5539496))))

      (println "\n🎄Day 5")
      (time (testing "Day 5 - part 1" (is (= (day-05/part-1) 57075758N))))
      (time (testing "Day 5 - part 2" (is (= (day-05/part-2) "I gave up"))))

      (println "\n🎄Day 6")
      (time (testing "Day 6 - part 1" (is (= (day-06/part-1) 128700))))
      (time (testing "Day 6 - part 2" (is (= (day-06/part-2) 39594072))))

      (println "\n🎄Day 7")
      (time (testing "Day 7 - part 1" (is (= (day-07/part-1) 249204891))))
      (time (testing "Day 7 - part 2" (is (= (day-07/part-2) 249666369))))

      (println "\n🎄Day 8")
      (time (testing "Day 8 - part 1" (is (= (day-08/part-1) 19951))))
      (time (testing "Day 8 - part 2" (is (= (day-08/part-2) 16342438708751))))

      (println "\n🎄Day 9")
      (time (testing "Day 9 - part 1" (is (= (day-09/part-1) 2098530125))))
      (time (testing "Day 9 - part 2" (is (= (day-09/part-2) 1016))))

      (println "\n🎄Day 10")
      (time (testing "Day 10 - part 1" (is (= (day-10/part-1) 6882))))
      ;(time (testing "Day 10 - part 2" (is (= (day-10/part-2) 0))))

      (println "\n🎄Day 11")                                ; with a little help :)
      (time (testing "Day 11 - part 1" (is (= (day-11/part-1) 9312968))))
      (time (testing "Day 11 - part 2" (is (= (day-11/part-2) 597714117556))))

      (println "\n🎄Day 12")
      (time (testing "Day 12 - part 1" (is (= (day-12/part-1) 8180))))
      (time (testing "Day 12 - part 2" (is (= (day-12/part-2) "The test input already takes way too long to run..."))))

      (println "\n🎄Day 13")
      (time (testing "Day 13 - part 1" (is (= (day-13/part-1) 34202))))
      (time (testing "Day 13 - part 2" (is (= (day-13/part-2) 34230))))

      (println "\n🎄Day 14")
      (time (testing "Day 14 - part 1" (is (= (day-14/part-1) 108813))))
      (time (testing "Day 14 - part 2" (is (= (day-14/part-2) "Another time maybe, not now..."))))

      (println "\n🎄Day 15")
      (time (testing "Day 15 - part 1" (is (= (day-15/part-1) 498538)))) ; fails with a difference of 10
      (time (testing "Day 15 - part 2" (is (= (day-15/part-2) 286278))))

      (println "\n🎅🏻Total time:"))))

(comment (run-timed-tests))
