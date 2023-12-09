(ns year2023.calendar
  (:require
    [clojure.test :refer [testing is]]
    [clojure.tools.namespace.repl :refer [refresh]]
    [year2023.day1.puzzles :as day-1]
    [year2023.day2.puzzles :as day-2]
    [year2023.day3.puzzles :as day-3]
    [year2023.day4.puzzles :as day-4]
    [year2023.day5.puzzles :as day-5]
    [year2023.day6.puzzles :as day-6]
    [year2023.day7.puzzles :as day-7]
    [year2023.day8.puzzles :as day-8]
    [year2023.day9.puzzles :as day-9]))

(def go refresh)

(comment go)

(defn run-timed-tests []
  (println "✨ 2023 ✨")
  (time
    (do
      (println "🎄Day 1")
      (time (testing "Day 1 - part 1" (is (= (day-1/part-1) 55029))))
      (time (testing "Day 1 - part 2" (is (= (day-1/part-2) 55686))))

      (println "\n🎄Day 2")
      (time (testing "Day 2 - part 1" (is (= (day-2/part-1) 2331))))
      (time (testing "Day 2 - part 2" (is (= (day-2/part-2) 71585))))

      (println "\n🎄Day 3")
      (time (testing "Day 3 - part 1" (is (= (day-3/part-1) 528819))))
      (time (testing "Day 3 - part 2" (is (= (day-3/part-2) 80403602))))

      (println "\n🎄Day 4")
      (time (testing "Day 4 - part 1" (is (= (day-4/part-1) 21821))))
      (time (testing "Day 4 - part 2" (is (= (day-4/part-2) 5539496))))

      (println "\n🎄Day 5")
      (time (testing "Day 5 - part 1" (is (= (day-5/part-1) 57075758N))))
      (time (testing "Day 5 - part 2" (is (= (day-5/part-2) "I gave up"))))

      (println "\n🎄Day 6")
      (time (testing "Day 6 - part 1" (is (= (day-6/part-1) 128700))))
      (time (testing "Day 6 - part 2" (is (= (day-6/part-2) 39594072))))

      (println "\n🎄Day 7")
      (time (testing "Day 7 - part 1" (is (= (day-7/part-1) 249204891))))
      (time (testing "Day 7 - part 2" (is (= (day-7/part-2) 249666369))))

      (println "\n🎄Day 8")
      (time (testing "Day 8 - part 1" (is (= (day-8/part-1) 19951))))
      (time (testing "Day 8 - part 2" (is (= (day-8/part-2) 16342438708751))))

      (println "\n🎄Day 9")
      (time (testing "Day 9 - part 1" (is (= (day-9/part-1) 2098530125))))
      (time (testing "Day 9 - part 2" (is (= (day-9/part-2) 1016))))

      (println "\n🎅🏻Total time:"))))

(comment (run-timed-tests))
