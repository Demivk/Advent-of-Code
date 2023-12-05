(ns year2023.day5.puzzles
  (:require
    [clojure.string :as string]
    [utils :as utils]))

(def input (slurp (utils/input-file-path 2023 5)))

(defn map-source-category [source-category]
  (let [[_ numbers] (string/split source-category #" map:\n")]
    (->>
      (utils/read-rows numbers)
      (mapv utils/get-big-ints)
      (mapv
        (fn [[destination-range-start source-range-start range-length]]
          (let [diff (- destination-range-start source-range-start)
                source-range-end (+ source-range-start (dec range-length))]
            {:source [source-range-start source-range-end]  ; = seed
             :diff diff}))))))

(defn in-range? [[start end] number] (and (>= number start) (<= number end)))

(defn seed-paths [seeds source-categories]
  (mapv
    (fn [seed]
      (loop [locations [seed]
             i 0]
        (if (>= i (count source-categories))
          locations
          (let [source-category (nth source-categories i)
                location (last locations)
                new-location (or
                               (some
                                 (fn [{:keys [source diff]}]
                                   (when (in-range? source location)
                                     (+ location diff)))
                                 source-category)
                               location)]
            (recur (conj locations new-location) (inc i))))))
    seeds))

(defn part-1 []
  (let [[seeds & source-categories] (string/split input #"\n\n")
        seeds (utils/get-big-ints seeds)
        source-categories (mapv map-source-category source-categories)
        seed-paths (seed-paths seeds source-categories)]
    (apply min (mapv last seed-paths))))

(comment (time (part-1)))

; The plan for part 2:
; Partition 2 the seeds and create ranges using [start (+ start (dec length))
; For each seed range, find overlapping for each source-category(?) -> location = start of range(?)
; Do this until you end up with a sequence of seed ranges
; (apply min (mapv start ranges))
(defn part-2 [] "I gave up")
