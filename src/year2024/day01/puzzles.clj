(ns year2024.day01.puzzles)

(def input (slurp (utils/input-file-path 2024 1)))

(defn part-1 []
  (let [pairs (map #(mapv utils/parse-int (utils/split-all-whitespaces %)) (utils/split-new-line input))
        left-list (sort (map first pairs))
        right-list (sort (map last pairs))]
    (apply + (map (fn [n1 n2] (- (max n1 n2) (min n1 n2))) left-list right-list))))

(defn part-2 []
  (let [pairs (map #(mapv utils/parse-int (utils/split-all-whitespaces %)) (utils/split-new-line input))
        left-list (sort (map first pairs))
        right-list (sort (map last pairs))]
    (apply +
      (map
        (fn [n1 _]
          (let [appear-count (count (filter #(= % n1) right-list))]
            (* n1 appear-count)))
        left-list right-list))))
