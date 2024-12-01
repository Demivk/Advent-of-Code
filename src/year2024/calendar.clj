(ns year2024.calendar
  (:require
    [clojure.test :refer [testing is]]
    [clojure.tools.namespace.repl :refer [refresh]]
    [year2024.day01.puzzles :as day-01]))

(def go refresh)

(comment go)

(defn run-timed-tests []
  (println "✨ 2024 ✨")
  (time
    (do
      (println "🎄Day 1")
      (time (testing "Day 1 - part 1" (is (= (day-01/part-1) 3574690))))
      (time (testing "Day 1 - part 2" (is (= (day-01/part-2) 22565391))))

      (println "\n🎄Day n")
      (time (testing "Day n - part 1" (is (= 1 1))))
      (time (testing "Day n - part 2" (is (= 2 2))))

      (println "\n🎅🏻Total time:"))))

(comment (run-timed-tests))
