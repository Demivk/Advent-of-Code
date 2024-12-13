(ns year2024.day12.puzzles
  (:require
    [clojure.set :as set]))

(def input (slurp (utils/input-file-path 2024 12)))

(defn find-perimeter [coords]
  (reduce
    (fn [perimeter [x y]]
      (->>
        (utils/get-cardinal-coords x y)
        (filter #(not (contains? coords %)))
        (count)
        (+ perimeter)))
    0
    coords))

(defn bfs [grid start-coord visited]
  (loop [queue [start-coord]
         visited (conj visited start-coord)]
    (if (empty? queue)
      visited
      (let [[current & remaining] queue
            [x y] current
            current-value (utils/get-cell-value grid current)
            neighbour-coords (filterv
                               #(and
                                  (utils/in-bounds? grid %)
                                  (not (contains? visited %))
                                  (= current-value (utils/get-cell-value grid %)))
                               (utils/get-cardinal-coords x y))]
        (recur
          (into remaining neighbour-coords)
          (into visited neighbour-coords))))))

(defn find-plots [grid]
  (loop [all-coords (into #{} (utils/grid->all-coords grid))
         plots []]
    (if (empty? all-coords)
      plots
      (let [checking (first all-coords)
            plot-coords (bfs grid checking #{})
            areas (conj plots
                    {:coords plot-coords
                     :value (utils/get-cell-value grid checking)
                     :area (count plot-coords)
                     :perimeter (find-perimeter plot-coords)})
            visited (into #{} (mapcat :coords areas))]
        (recur (set/difference all-coords visited) areas)))))

(defn part-1 []
  (->>
    (utils/read-grid input)
    (find-plots)
    (mapv (fn [{:keys [area perimeter]}] (* area perimeter)))
    (apply +)))

(defn part-2 [] 0)
