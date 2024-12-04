(ns year2024.calendar
  (:require
    [clojure.test :refer [testing is]]
    [clojure.tools.namespace.repl :refer [refresh]]
    [year2024.day01.puzzles :as day-01]
    [year2024.day02.puzzles :as day-02]
    [year2024.day03.puzzles :as day-03]
    [year2024.day04.puzzles :as day-04]))

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

      (println "\n🎅🏻Total time:"))))

(comment (run-timed-tests))
