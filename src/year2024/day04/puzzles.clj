(ns year2024.day04.puzzles)

(def input (slurp (utils/input-file-path 2024 4)))

(defn apply-direction [coord direction]
  [(+ (first coord) (first direction)) (+ (last coord) (last direction))])

(defn part-1 []
  (let [grid (utils/read-grid input)
        hashmap (utils/grid->coords-hashmap grid)
        start-positions (get hashmap "X")]
    (->
      (reduce
        (fn [acc [x y]]
          (let [m-coords (into #{} (get hashmap "M"))
                surrounding-m-coords (filterv #(contains? m-coords %) (utils/get-all-surrounding-coords x y))
                valid-words (mapv
                              (fn [coord]
                                (let [direction [(- (first coord) x) (- (last coord) y)]
                                      next-coord (apply-direction coord direction)]
                                  (when (= (utils/get-cell-value grid next-coord) "A")
                                    (let [next-next-coord (apply-direction next-coord direction)]
                                      (= (utils/get-cell-value grid next-next-coord) "S")))))
                              surrounding-m-coords)]
            (into acc (filterv true? valid-words))))
        []
        start-positions)
      count)))

(defn part-2 []
  (let [grid (utils/read-grid input)
        hashmap (utils/grid->coords-hashmap grid)
        start-positions (get hashmap "A")]
    (->
      (reduce
        (fn [acc [x y]]
          (let [[tl _ tr _ br _ bl _] (utils/get-all-surrounding-values grid x y)
                pairs #{["M" "S"] ["S" "M"]}
                valid? (and (contains? pairs [tl br]) (contains? pairs [tr bl]))]
            (cond-> acc valid? (conj true))))
        []
        start-positions)
      count)))
