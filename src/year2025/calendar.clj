(ns year2025.calendar
  (:require
    [clojure.test :refer [testing is]]
    [clojure.tools.namespace.repl :refer [refresh]]
    [year2025.day01.puzzles :as day-01]
    [year2025.day02.puzzles :as day-02]))

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

      (println "\n🎅🏻Total time:"))))

(comment (run-timed-tests))
