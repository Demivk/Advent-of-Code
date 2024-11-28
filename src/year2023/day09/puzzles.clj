(ns year2023.day09.puzzles)

(def input (slurp (utils/input-file-path 2023 9)))

(defn sequence->differences [sequence] (mapv #(-> %2 (- %1)) sequence (rest sequence)))

(defn walk-history
  "Starting with the given history, finds the differences between the
  values and uses it as the new sequnce. It repeats this process of finding
  the differences of each new sequence until all differences are equal to
  zero. Returns all the sequences."
  [start-history]
  (loop [sequences [start-history]]
    (if (every? zero? (last sequences))
      sequences
      (let [sequence (last sequences)
            differences (sequence->differences sequence)]
        (recur (conj sequences differences))))))

(defn find-prediction
  "Given a list of sequences, transforms it into a reverse list of all
   first or last values. It calculates each placeholder using the
   sequence above (= next in reversed). Returns the prediction of the
   first (= last) sequence.

   When looking for the first prediction, the placeholder is the first
   number of the sequence above - the first number of the current
   sequence.

   When looking for the last prediction, the placeholder is the last
   number of the current sequence + the last number of the sequence
   above."
  [sequences part]
  (let [reversed (mapv (if (= part 1) last first) (reverse sequences))]
    (loop [placeholders [0]
           next-i 1]
      (if (>= next-i (count reversed))
        (last placeholders)
        (let [current-placeholder (last placeholders)
              next-placeholder (nth reversed next-i)
              new-placeholder (if (= part 1)
                                (+ current-placeholder next-placeholder)
                                (- next-placeholder current-placeholder))]
          (recur (conj placeholders new-placeholder) (inc next-i)))))))

(defn find-last-prediction [sequences] (find-prediction sequences 1))

(defn find-first-prediction [sequences] (find-prediction sequences 2))

(defn part-1 []
  (let [histories (mapv utils/get-ints (utils/read-rows input))]
    (->>
      histories
      (mapv #(find-last-prediction (walk-history %)))
      (apply +))))

(defn part-2 []
  (let [histories (mapv utils/get-ints (utils/read-rows input))]
    (->>
      histories
      (mapv #(find-first-prediction (walk-history %)))
      (apply +))))
