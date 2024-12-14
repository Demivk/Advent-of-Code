(ns year2024.day14.puzzles)

(def input (slurp (utils/input-file-path 2024 14)))

(def width 101)
(def height 103)

(defn input->robot-movements []
  (->>
    (utils/split-new-line input)
    (map
      (fn [movements]
        (let [[p v] (utils/split-whitespace movements)
              start-pos (utils/get-ints p)
              delta (utils/get-ints v)]
          [start-pos delta])))))

(defn move [pos delta second grid]
  (let [[x y] pos
        [dx dy] delta
        grid-width (count (first grid))
        grid-height (count grid)
        new-x (mod (+ x (* dx second)) grid-width)
        new-y (mod (+ y (* dy second)) grid-height)]
    [(if (neg? new-x) (+ new-x grid-width) new-x)
     (if (neg? new-y) (+ new-y grid-height) new-y)]))

(defn group-coords [qx qy coords]
  (loop [coords coords
         groups {:tl []
                 :tr []
                 :bl []
                 :br []}]
    (if (empty? coords)
      (update-vals groups count)
      (let [[coord & remaining] coords
            [x y] coord
            coord-group (cond
                          (and (< x qx) (< y qy)) :tl
                          (and (> x qx) (< y qy)) :tr
                          (and (< x qx) (> y qy)) :bl
                          (and (> x qx) (> y qy)) :br)]
        (recur remaining (update groups coord-group #(conj % coord)))))))

(defn part-1 []
  (let [grid (utils/make-grid width height)
        qx (quot width 2)
        qy (quot height 2)]
    (->>
      (input->robot-movements)
      (map (fn [[pos delta]] (move pos delta 100 grid)))
      (filter (fn [[x y]] (not (or (= x qx) (= y qy)))))
      (group-coords qx qy)
      (vals)
      (apply *))))

(defn part-2
  ([] (part-2 false))
  ([print-grid?]
   (let [grid (utils/make-grid width height)]
     (loop [step 0]
       (let [coords (map
                      (fn [[pos delta]] (move pos delta step grid))
                      (input->robot-movements))
             is-tree? (= (count coords) (count (distinct coords)))]
         (if is-tree?
           (do (when print-grid? (utils/draw-coords-on-grid width height coords)) step)
           (recur (inc step))))))))
