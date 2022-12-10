(ns day9.day9
  (:require
    [clojure.string :as string]
    [clojure.edn :as edn]))

(defn read-input [] (string/split (slurp "src/day9/input.edn") #"\n"))

(defn abs [n] (max n (- n)))

(defn signum [delta]
  (cond
    (zero? delta) 0
    (> delta 0) 1
    :else -1))

(defn motion->direction-seq [motion]
  (let [[direction steps] (string/split motion #" ")]
   (vec (repeat (edn/read-string steps) direction))))

(defn touching? [[head-x head-y] [tail-x tail-y]]
  (let [delta-x (- tail-x head-x)
        delta-y (- tail-y head-y)]
    (and (<= (abs delta-x) 1) (<= (abs delta-y) 1))))

(defn move-head [[x y] direction]
  (case direction
    "U" [x (inc y)]
    "D" [x (dec y)]
    "L" [(dec x) y]
    "R" [(inc x) y]))

(defn move-tail [[head-x head-y] [tail-x tail-y]]
  (let [delta-x (- tail-x head-x)
        delta-y (- tail-y head-y)]
    (if (and (<= (abs delta-x) 1) (<= (abs delta-y) 1))
      [tail-x tail-y]
      (->>
        (mapv - [head-x head-y] [tail-x tail-y])
        (mapv #(signum %))
        (mapv + [tail-x tail-y])))))

(defn get-tail-positions [direction-seq]
  (let [head-position (atom [0 0])]
    (reduce
      (fn [tail-positions direction]
        (let [tail-position (last tail-positions)
              new-head-position (move-head @head-position direction)
              _ (reset! head-position new-head-position)]
          (conj tail-positions
            (if (touching? @head-position tail-position)
              tail-position
              (move-tail @head-position tail-position)))))
      [[0 0]]
      direction-seq)))

(defn part-1 []
  (->>
    (read-input)
    (mapv #(motion->direction-seq %))
    (flatten)
    (get-tail-positions)
    (distinct)
    (count)))

(defn part-2 []
  (->>
    (read-input)
    (mapv #(motion->direction-seq %))
    (flatten)))

(comment
  (part-1))
