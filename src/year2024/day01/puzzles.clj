(ns year2024.day01.puzzles)

(def input (slurp (utils/input-file-path 2024 1)))

(defn part-1 []
  (let [pairs (map #(mapv utils/parse-int (utils/split-all-whitespaces %)) (utils/split-new-line input))
        l (sort (map first pairs))
        r (sort (map last pairs))]
    (->>
      (map #(abs (- %1 %2)) l r)
      (apply +))))

(defn part-2 []
  (let [pairs (map #(mapv utils/parse-int (utils/split-all-whitespaces %)) (utils/split-new-line input))
        l (sort (map first pairs))
        r (sort (map last pairs))]
    (->>
      (map (fn [n1 _] (let [appear-count (count (filter #(= % n1) r))] (* n1 appear-count))) l r)
      (apply +))))
