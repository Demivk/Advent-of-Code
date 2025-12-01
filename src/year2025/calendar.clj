(ns year2025.calendar
  (:require
    [clojure.test :refer [testing is]]
    [clojure.tools.namespace.repl :refer [refresh]]
    [year2025.day01.puzzles :as day-01]))

(def go refresh)

(comment go)

(defn run-timed-tests []
  (println "✨ 2025 ✨")
  (time
    (do
      (println "🎄Day 1")
      (time (testing "Day 1 - part 1" (is (= (day-01/part-1) 1154))))
      (time (testing "Day 1 - part 2" (is (= (day-01/part-2) 6819))))

      (println "\n🎅🏻Total time:"))))

(comment (run-timed-tests))
