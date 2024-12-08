(ns year2024.day08.puzzles)

(def input (slurp (utils/input-file-path 2024 8)))

(defn part-1 []
  (let [grid (utils/read-grid input)
        hashmap (->
                  (utils/grid->coords-hashmap grid)
                  (dissoc "."))
        antinode-coords (reduce
                          (fn [all-antinode-coords [_ coords]]
                            (into
                              all-antinode-coords
                              (mapcat
                                identity
                                (for [i (range (count coords))
                                      j (range (inc i) (count coords))]
                                  (let [coord-1 (nth coords i)
                                        coord-2 (nth coords j)
                                        delta (utils/delta coord-1 coord-2)]
                                    (filter
                                      #(utils/in-bounds? grid %)
                                      [(utils/subtract-delta coord-1 delta)
                                       (utils/add-delta coord-2 delta)]))))))
                          #{}
                          hashmap)]
    (count antinode-coords)))

(defn get-next-coords [grid coord delta add?]
  (loop [coord coord
         result []]
    (let [new-coord (if add? (utils/add-delta coord delta) (utils/subtract-delta coord delta))]
      (if (utils/in-bounds? grid new-coord)
        (recur new-coord (conj result new-coord))
        result))))

(defn part-2 []
  (let [grid (utils/read-grid input)
        hashmap (->
                  (utils/grid->coords-hashmap grid)
                  (dissoc "."))
        antinode-coords (reduce
                          (fn [all-antinode-coords [_ coords]]
                            (into
                              all-antinode-coords
                              (mapcat
                                identity
                                (for [i (range (count coords))
                                      j (range (inc i) (count coords))]
                                  (let [coord-1 (nth coords i)
                                        coord-2 (nth coords j)
                                        delta (utils/delta coord-1 coord-2)]
                                    (concat
                                      [coord-1 coord-2]
                                      (get-next-coords grid coord-1 delta false)
                                      (get-next-coords grid coord-2 delta true)))))))
                          #{}
                          hashmap)]
    (count antinode-coords)))
