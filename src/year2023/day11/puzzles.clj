(ns year2023.day11.puzzles
  (:require
    [clojure.math :as math]))

(def input (slurp (utils/input-file-path 2023 11)))

(defn empty-space-lines [grid]
  (->>
    grid
    (keep-indexed (fn [i row] (when (not (some #(= % \#) row)) i)))
    (into #{})))

(defn get-delta ^long [expand? ^long expansion ^long a ^long b]
  (let [step (long (math/signum (- b a)))]
    (loop [delta 0
           position a]
      (if (= position b)
        delta
        (let [pos (+ position step)]
          (recur
            (if (expand? pos)
              (+ delta expansion)
              (inc delta))
            pos))))))

(defn get-distances
  [columns-to-expand rows-to-expand expansion [x1 y1] [x2 y2]]
  (+
    (get-delta columns-to-expand expansion x1 x2)
    (get-delta rows-to-expand expansion y1 y2)))

(defn solve
  [input expansion]
  (let [grid (utils/read-rows input)
        rows-to-expand (empty-space-lines grid)
        columns-to-expand (empty-space-lines (utils/transpose-grid grid))
        galaxy-coords (->>
                        (utils/read-grid input)
                        (map-indexed
                          (fn [row-index row]
                            (keep-indexed
                              (fn [col-index cell]
                                (when (= cell "#") [col-index row-index]))
                              row)))
                        (apply concat)
                        (into []))
        galaxy-pairs (into #{}
                       (for [a galaxy-coords
                             b galaxy-coords
                             :when (not= a b)]
                         #{a b}))]
    (->>
      galaxy-pairs
      (mapv #(get-distances columns-to-expand rows-to-expand expansion (first %) (second %)))
      (apply +))))

(defn part-1 [] (solve input 2))

(defn part-2 [] (solve input 1000000))
