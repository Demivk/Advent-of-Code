(ns year2025.day01.puzzles
  (:require
    [utils]))

(def input (slurp (utils/input-file-path 2025 1)))

(defn get-password [track-passes?]
  (let [input (->>
                (slurp (utils/input-file-path 2025 1))
                (utils/split-new-line)
                (mapv (fn [s] [(subs s 0 1) (utils/parse-int (subs s 1))])))]
    (loop [rotations input
           last-result 50
           zero-counter 0]
      (let [[[rotation distance] & remaining] rotations
            result (->
                     (if (= rotation "L") (- distance) distance)
                     (+ last-result)
                     (mod 100))
            passes (if (zero? distance)
                     distance
                     (let [passed? (if (pos? distance) (< result last-result) (> result last-result))]
                       (+ (quot distance 100) (if passed? 1 0))))]
        (if (seq remaining)
          (recur
            remaining
            result
            (cond-> zero-counter
              track-passes? (+ passes)
              (and (not track-passes?) (zero? result)) inc))
          zero-counter)))))

(defn part-1 [] (get-password false))

(defn part-2 [] (get-password true))
