(ns year2025.calendar
  (:require
    [clojure.test :refer [testing is]]
    [clojure.tools.namespace.repl :refer [refresh]]
    [year2025.day01.puzzles :as day-01]
    [year2025.day02.puzzles :as day-02]
    [year2025.day03.puzzles :as day-03]
    [year2025.day04.puzzles :as day-04]
    [year2025.day05.puzzles :as day-05]
    [year2025.day06.puzzles :as day-06]))

(def go refresh)

(comment go)

(defn run-timed-tests []
  (println "✨ 2025 ✨")
  (time
    (do
      (println "🎄Day 1")
      (time (testing "Day 1 - part 1" (is (= (day-01/part-1) 1154))))
      (time (testing "Day 1 - part 2" (is (= (day-01/part-2) 6819))))

      (println "🎄Day 2")
      (time (testing "Day 2 - part 1" (is (= (day-02/part-1) 41294979841))))
      (time (testing "Day 2 - part 2" (is (= (day-02/part-2) 66500947346))))

      (println "🎄Day 3")
      (time (testing "Day 3 - part 1" (is (= (day-03/part-1) 17430))))
      (time (testing "Day 3 - part 2" (is (= (day-03/part-2) 171975854269367))))

      (println "🎄Day 4")
      (time (testing "Day 4 - part 1" (is (= (day-04/part-1) 1491))))
      (time (testing "Day 4 - part 2" (is (= (day-04/part-2) 8722))))

      (println "🎄Day 5")
      (time (testing "Day 5 - part 1" (is (= (day-05/part-1) 525))))
      (time (testing "Day 5 - part 2" (is (= (day-05/part-2) 333892124923577))))

      (println "🎄Day 06")
      (time (testing "Day 06 - part 1" (is (= (day-06/part-1) 6209956042374))))
      (time (testing "Day 06 - part 2" (is (= (day-06/part-2) 12608160008022))))

      (println "\n🎅🏻Total time:"))))

(comment (run-timed-tests))
