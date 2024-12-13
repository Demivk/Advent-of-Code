(ns year2024.day10.puzzles)

(def input (slurp (utils/input-file-path 2024 10)))

(defn bfs
  "Breadth-First Search (BFS)

  Given a grid, start coord and visited coll, this function performs a breadth-first traversal of the grid. It
  looks for valid paths where adjacent cells have incrementing int values starting from the value (in most cases
  probably 0) at the `trailhead` coord. Returns the amount of valid paths that end at a cell with value `target`
  Cells cannot be revisted."
  [grid trailhead visited]
  (loop [queue [trailhead]
         visited visited
         valid-paths 0]
    (if (empty? queue)
      valid-paths
      (let [[current & remaining] queue
            [x y] current
            current-value (utils/get-cell-value grid current)
            ; Remove the neighbours that are out of bounds, already visited and are not equal to the next value
            neighbours (filterv
                         #(and
                            (utils/in-bounds? grid %)
                            (not (contains? visited %))
                            (= (inc current-value) (utils/get-cell-value grid %)))
                         (utils/get-cardinal-coords x y))]
        (recur
          (into remaining neighbours)
          (into visited neighbours)
          (if (= 9 current-value) (inc valid-paths) valid-paths))))))

(defn part-1 []
  (let [grid (mapv #(mapv utils/parse-int %) (utils/read-grid input))
        trailheads (->
                     (utils/grid->coords-hashmap grid)
                     (get 0))]
    (->>
      trailheads
      (map #(bfs grid % #{}))
      (apply +))))

(defn part-2 []
  (let [grid (mapv #(mapv utils/parse-int %) (utils/read-grid input))
        trailheads (->
                     (utils/grid->coords-hashmap grid)
                     (get 0))]
    (->>
      trailheads
      (map #(bfs grid % []))
      (apply +))))
