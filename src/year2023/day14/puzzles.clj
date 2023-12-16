(ns year2023.day14.puzzles
  (:require
    [clojure.string :as string]
    [utils :as utils]))

(def input (slurp (utils/input-file-path 2023 14)))

(defn get-sorted-parts [col]
  (->>
    (string/split (apply str col) #"#")
    (mapv #(mapv str (reverse (sort %))))
    (apply concat)))

(defn sort-column [col]
  (let [split-sorted (get-sorted-parts col)]
    (loop [new-col []
           col-i 0
           sorted-i 0]
      (if (>= col-i (count col))
        new-col
        (let [value (nth col col-i)]
          (if (= value "#")
            (recur (conj new-col "#") (inc col-i) sorted-i)
            (recur (conj new-col (nth split-sorted sorted-i)) (inc col-i) (inc sorted-i))))))))

(defn part-1 []
  (let [tilted (->>
                 (utils/read-grid input)
                 (utils/transpose-grid)
                 (mapv sort-column)
                 (utils/transpose-grid))
        total-loads (->>
                      (rseq tilted)
                      (map-indexed (fn [i row] [(inc i) row]))
                      (reduce
                        (fn [row-loads [nr row]]
                          (conj row-loads (* (count (filterv #(= % "O") row)) nr)))
                        []))]
    (apply + total-loads)))

(defn part-2 [] "Another time maybe, not now...")
