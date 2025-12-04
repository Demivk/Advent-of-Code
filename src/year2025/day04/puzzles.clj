(ns year2025.day04.puzzles
  (:require
    [utils]))

(def input (slurp (utils/input-file-path 2025 4)))

(defn get-removable-rolls-coords [grid]
  (reduce
    (fn [coords [x y]]
      (if (= (utils/get-cell-value grid x y) "@")
        (let [adjacent-rolls-count (->>
                                     (utils/get-all-surrounding-values grid x y)
                                     (filterv #(= % "@"))
                                     (count))]
          (cond-> coords (< adjacent-rolls-count 4) (conj [x y])))
        coords))
    []
    (utils/grid->all-coords grid)))

(defn part-1 []
  (->
    (utils/read-grid input)
    (get-removable-rolls-coords)
    (count)))

(defn part-2 []
  (loop [grid (utils/read-grid input)
         removable-roll-count 0]
    (if-let [removable-coords (seq (get-removable-rolls-coords grid))]
      (let [updated-grid (reduce
                           (fn [grid [x y]]
                             (utils/set-cell-value grid x y "."))
                           grid
                           removable-coords)]
        (recur updated-grid (+ removable-roll-count (count removable-coords))))
      removable-roll-count)))
