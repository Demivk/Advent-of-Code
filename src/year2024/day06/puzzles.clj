(ns year2024.day06.puzzles)

(def input (slurp (utils/input-file-path 2024 6)))

(defn direction->next-coord [direction x y]
  (case direction
    :top (utils/top-coord x y)
    :right (utils/right-coord x y)
    :bottom (utils/bottom-coord x y)
    :left (utils/left-coord x y)))

(defn rotate-direction [direction]
  (case direction
    :top :right
    :right :bottom
    :bottom :left
    :left :top))

(defn walk-path [grid path direction]
  (let [[current-x current-y] (last path)
        [t r b l] (utils/get-cardinal-values grid current-x current-y)
        can-continue? (some?
                        (case direction
                          :top t
                          :right r
                          :bottom b
                          :left l))]
    (if can-continue?
      (let [next-coord (direction->next-coord direction current-x current-y)
            next-value (utils/get-cell-value grid next-coord)]
        (if (= next-value "#")
          (recur grid path (rotate-direction direction))
          (recur grid (conj path next-coord) direction)))
      path)))

(defn part-1 []
  (let [grid (utils/read-grid input)
        start-coord (utils/value->coord grid "^")
        walked-path (walk-path grid [start-coord] :top)]
    (count (distinct walked-path))))

(defn part-2 []
  (let [grid (utils/read-grid input)
        start-coord (utils/value->coord grid "^")
        walked-path (walk-path grid [start-coord] :top)]
    (->>
      (distinct walked-path)
      (drop 1)
      (map
        (fn [[obs-x obs-y]]
          (let [new-grid (assoc-in grid [obs-y obs-x] "#")]
            (loop [coord start-coord
                   direction :top
                   visited #{}]
              (let [next-coord (direction->next-coord direction (first coord) (last coord))
                    next-value (utils/get-cell-value new-grid next-coord)
                    state [coord direction]]
                (cond
                  (contains? visited state) 1
                  (= next-value "#") (recur coord (rotate-direction direction) (conj visited state))
                  (or (= next-value ".") (= next-value "^")) (recur next-coord direction (conj visited state))
                  :else 0))))))
      (apply +))))
