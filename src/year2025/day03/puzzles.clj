(ns year2025.day03.puzzles
  (:require
    [utils]))

(def input (slurp (utils/input-file-path 2025 3)))

(defn find-max-joltage [str-batteries n]
  (let [batteries (mapv utils/parse-long (utils/split-every-character str-batteries))]
    (loop [joltage []
           start-i 0]
      (if (= (count joltage) n)
        (utils/parse-long (apply str joltage))
        (let [n-batteries-needed (- n (count joltage))
              n-batteries-to-check (- (count batteries) start-i)
              end-offset (- n-batteries-to-check n-batteries-needed)
              trimmed-batteries (subvec batteries start-i (inc (+ start-i end-offset)))
              largest-battery (apply max trimmed-batteries)
              largest-battery-i (+ start-i (.indexOf trimmed-batteries largest-battery))]
          (recur (conj joltage largest-battery) (inc largest-battery-i)))))))

(defn part-1 []
  (->>
    input
    (utils/split-new-line)
    (map #(find-max-joltage % 2))
    (apply +)))

(defn part-2 []
  (->>
    input
    (utils/split-new-line)
    (map #(find-max-joltage % 12))
    (apply +)))
