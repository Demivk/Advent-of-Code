(ns year2023.day3.puzzles
  (:require
    [utils :as utils]))

(def input (slurp (utils/input-file-path 2023 3)))

(defn is-symbol? [s] (and (some? s) (not= s ".") (not (utils/num? s))))

(defn symbol-coords [grid]
  (let [rows (count grid)
        cols (count (first grid))
        coords (for [row (range rows)
                     col (range cols)]
                 [row col])
        valid? (fn [row col rows cols] (and (>= row 0) (< row rows) (>= col 0) (< col cols)))]
    (->>
      coords
      (filterv
        (fn [[row col]]
          (let [cell (str (get-in grid [row col]))]
            (is-symbol? cell))))
      (filterv
        (fn [[row col]]
          (let [surrounding (for [r (range (dec row) (+ row 2))
                                  c (range (dec col) (+ col 2))]
                              [r c])]
            (every? (fn [[r c]] (valid? r c rows cols)) surrounding)))))))

(defn number-coords-and-value []
  (->>
    (utils/read-rows input)
    (map-indexed
      (fn [y row]
        (loop [matcher (re-matcher #"\d+" row)
               acc []]
          (if-let [next-match (re-find matcher)]
            (recur matcher (conj acc [[y (. matcher (start)) (. matcher (end))] next-match]))
            acc))))
    (apply concat)))

(defn adjacent? [row col x-start x-end y] (and (<= (dec row) y (inc row)) (<= (dec x-start) col x-end)))

(defn part-1 []
  (let [grid (utils/read-grid input)
        symbol-coords (symbol-coords grid)
        number-coords-and-value (number-coords-and-value)]
    (reduce
      (fn [acc [[y x-start x-end] num]]
        (if (some (fn [[row col]] (adjacent? row col x-start x-end y)) symbol-coords)
          (+ acc (utils/parse-int num))
          acc))
      0
      number-coords-and-value)))

(defn part-2 []
  (let [grid (utils/read-grid input)
        symbol-coords (mapv (fn [[row cell]] [[row cell] (str (get-in grid [row cell]))]) (symbol-coords grid))
        number-coords-and-value (number-coords-and-value)]
    (reduce
      (fn [acc [[row col] sym]]
        (if (not= sym "*")
          acc
          (let [numbers (keep
                          (fn [[[y x-start x-end] num]]
                            (when (adjacent? row col x-start x-end y) (utils/parse-int num)))
                          number-coords-and-value)]
            (cond-> acc (= (count numbers) 2) (+ (apply * numbers))))))
      0
      symbol-coords)))
