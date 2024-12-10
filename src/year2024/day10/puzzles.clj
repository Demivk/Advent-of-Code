(ns year2024.day10.puzzles)

(def input (slurp (utils/input-file-path 2024 10)))

(defn part-1 []
  (let [grid (mapv #(mapv utils/parse-int %) (utils/read-grid input))
        trailheads (->
                     (utils/grid->coords-hashmap grid)
                     (get 0))]
    (->>
      trailheads
      (map #(utils/bfs grid % #{%} 9))
      (apply +))))

(defn part-2 []
  (let [grid (mapv #(mapv utils/parse-int %) (utils/read-grid input))
        trailheads (->
                     (utils/grid->coords-hashmap grid)
                     (get 0))]
    (->>
      trailheads
      (map #(utils/bfs grid % [%] 9))
      (apply +))))
