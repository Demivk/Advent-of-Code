(ns year2025.day02.puzzles
  (:require
    [clojure.string :as string]
    [utils]))

(def input (slurp (utils/input-file-path 2025 2)))

(defn invalid-part-1? [str-id]
  (when (even? (count str-id))
    (let [half-length (/ (count str-id) 2)]
      (= (subs str-id 0 half-length) (subs str-id half-length (count str-id))))))

(defn invalid-part-2? [str-id]
  (some
    (fn [i]
      (let [partitioned (partition-all i str-id)]
        (and (>= (count partitioned) 2) (apply = partitioned))))
    (range 1 (inc (/ (count str-id) 2)))))

(defn part-1 []
  (reduce
    (fn [sum range-str]
      (let [[start end] (mapv utils/parse-long (string/split range-str #"-"))
            invalid-ids (keep #(when (invalid-part-1? (str %)) %) (range start (inc end)))]
        (->
          (apply + invalid-ids)
          (+ sum))))
    0
    (string/split (string/trim-newline input) #",")))

(defn part-2 []
  (reduce
    (fn [sum range-str]
      (let [[start end] (mapv utils/parse-long (string/split range-str #"-"))
            invalid-ids (keep #(when (invalid-part-2? (str %)) %) (range start (inc end)))]
        (->
          (apply + invalid-ids)
          (+ sum))))
    0
    (string/split (string/trim-newline input) #",")))
