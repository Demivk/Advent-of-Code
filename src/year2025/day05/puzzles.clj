(ns year2025.day05.puzzles
  (:require
    [clojure.string :as string]
    [utils]))

(def input (slurp (utils/input-file-path 2025 5)))

(defn part-1 []
  (let [[ranges ids] (utils/split-double-new-lines input)
        all-fresh-ids-ranges (->>
                               (utils/split-new-line ranges)
                               (mapv #(mapv utils/parse-long (string/split % #"-"))))
        ids (mapv utils/parse-long (utils/split-new-line ids))]
    (reduce
      (fn [total id]
        (cond-> total
          (some (fn [[start end]] (and (>= id start) (<= id end))) all-fresh-ids-ranges) inc))
      0
      ids)))

(defn part-2 []
  (let [[ranges _] (utils/split-double-new-lines input)
        sorted-ranges (->>
                        (utils/split-new-line ranges)
                        (map #(mapv utils/parse-long (string/split % #"-")))
                        (sort-by first))
        overlapping-ranges (reduce
                             (fn [new-ranges [start end]]
                               (if-let [[prev-start prev-end] (last new-ranges)]
                                 (if (<= start (inc prev-end))
                                   (into (vec (butlast new-ranges)) [[prev-start (max end prev-end)]])
                                   (conj new-ranges [start end]))
                                 (conj new-ranges [start end])))
                             []
                             sorted-ranges)]
    (reduce
      (fn [total [start end]]
        (+ total (inc (- end start))))
      0
      overlapping-ranges)))
