(ns day8.day8
  (:require
    [clojure.string :as string]
    [clojure.edn :as edn]
    [clojure.set :as set]))

(defn read-input [] (mapv #(mapv edn/read-string (string/split % #"")) (string/split (slurp "src/day8/input.edn") #"\n")))

(defn get-tree [grid x y] (nth (nth grid y) x))

(defn larger? [n coll] (every? #(> n %) coll))

(defn get-top [grid x y]
  (into []
    (for [row (subvec grid 0 y)]
      (nth row x))))

(defn get-bottom [grid x y]
  (into []
    (for [row (subvec grid (inc y))]
      (nth row x))))

(defn get-left [grid x y]
  (let [row (nth grid y)]
    (subvec row 0 x)))

(defn get-right [grid x y]
  (let [row (nth grid y)]
    (subvec row (inc x))))

(def apply-get-positions (juxt get-top get-bottom get-left get-right))

(defn tree-visible? [grid x y]
  (let [tree (get-tree grid x y)
        need-a-name (partial larger? tree)
        applied-positions (apply-get-positions grid x y)]
    (some identity (mapv need-a-name applied-positions))))

(defn part-1 []
  (let [grid (read-input)]
    (->>
     (for [y (range (count grid))]
       (for [x (range (count (first grid)))]
         (when (tree-visible? grid x y) [x y])))
      (mapv #(remove nil? %))
      (mapv #(set %))
      (apply set/union)
      (count))))

(defn scenic-score [n coll]
  (min (inc (count (take-while (partial > n) coll)))
    (count coll)))

(defn score [grid x y]
  (let [tree (get-tree grid x y)
        scenic-score (partial scenic-score tree)
        [top bottom left right] (apply-get-positions grid x y)]
    (reduce * (map scenic-score [(reverse left) right (reverse top) bottom]))))

(defn part-2 []
  (let [grid (read-input)]
    (->> (for [y (range (count grid))]
           (for [x (range (count (first grid)))]
             (score grid x y)))
      (flatten)
      (apply max))))
