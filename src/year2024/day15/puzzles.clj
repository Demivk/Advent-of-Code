(ns year2024.day15.puzzles
  (:require
    [clojure.string :as string]))

(def input (slurp (utils/input-file-path 2024 15)))

(defn input->warehouse-and-movements []
  (let [[warehouse movements] (utils/split-double-new-lines input)
        warehouse (mapv #(utils/split-every-character (string/trim %)) (utils/split-new-line warehouse))
        movements (remove #{"\n" " "} (utils/split-every-character movements))]
    [warehouse movements]))

(defn expand-warehouse [warehouse]
  (reduce
    (fn [expanded-warehouse row]
      (conj
        expanded-warehouse
        (reduce
          (fn [expanded-warehouse cell]
            (vec                                            ; TODO mapcat
              (flatten
                (conj expanded-warehouse
                  (case cell
                    "#" ["#" "#"]
                    "O" ["[" "]"]
                    "." ["." "."]
                    "@" ["@" "."])))))
          []
          row)))
    []
    warehouse))

(defn move-boxes [warehouse robot first-box-pos]
  (let [[rx ry] robot
        [fbx fby] first-box-pos
        [dx dy] (utils/delta robot first-box-pos)]
    (loop [coords [first-box-pos]
           [nx ny] (utils/add-delta first-box-pos [dx dy])]
      (let [value (utils/get-cell-value warehouse nx ny)]
        (case value
          "O" (recur (conj coords [nx ny]) (utils/add-delta [nx ny] [dx dy]))
          "." (let [updated-warehouse (->
                                        warehouse
                                        (assoc-in [fby fbx] "@")
                                        (assoc-in [ry rx] ".")
                                        (assoc-in [ny nx] "O"))]
                [updated-warehouse [fbx fby]])
          [warehouse robot])))))                            ; "#"

(def move
  (memoize
    (fn [warehouse movement [rx ry]]
      (let [[nx ny] (case movement
                      "^" (utils/top-coord rx ry)
                      ">" (utils/right-coord rx ry)
                      "v" (utils/bottom-coord rx ry)
                      "<" (utils/left-coord rx ry))]
        (case (utils/get-cell-value warehouse [nx ny])
          "." [(->
                 warehouse
                 (assoc-in [ry rx] ".")
                 (assoc-in [ny nx] "@"))
               [nx ny]]
          "#" [warehouse [rx ry]]
          "O" (move-boxes warehouse [rx ry] [nx ny]))))))

(defn part-1 []
  (let [[warehouse movements] (input->warehouse-and-movements)
        final-warehouse (loop [warehouse warehouse
                               movements movements
                               robot (utils/value->coord warehouse "@")]
                          (if (empty? movements)
                            warehouse
                            (let [[movement & remaining] movements
                                  [updated-warehouse new-robot] (move warehouse movement robot)]
                              (recur updated-warehouse remaining new-robot))))]
    (->>
      (utils/values->coords final-warehouse "O")
      (map (fn [[x y]] (+ (* y 100) x)))
      (apply +))))

(defn move-double-boxes [warehouse robot first-box-pos]
  (let [[rx ry] robot
        [dx dy] (utils/delta robot first-box-pos)]
    (println "Delta" [dx dy])
    (loop [coords [first-box-pos]
           [nx ny] (utils/add-delta first-box-pos [dx dy])]
      (let [value (utils/get-cell-value warehouse nx ny)]
        (case value
          ("[" "]") (recur (conj coords [nx ny]) (utils/add-delta [nx ny] [dx dy]))
          "." (cond
                (= dx 1) (let [row (get warehouse ny)
                               left (subvec row 0 rx)
                               to-move (subvec row rx nx)
                               right (subvec row (inc nx) (count row))
                               new-row (vec (concat left value to-move right))
                               updated-warehouse (assoc warehouse ny new-row)]
                           [updated-warehouse first-box-pos])
                (= dx -1) (let [row (get warehouse ny)
                                left (subvec row 0 nx)
                                to-move (subvec row (inc nx) (inc rx))
                                right (subvec row (inc rx) (count row))
                                new-row (vec (concat left to-move value right))
                                updated-warehouse (assoc warehouse ny new-row)]
                            [updated-warehouse first-box-pos])
                (= dy 1) (let [_ (println coords)
                               ; Idea:
                               ; For each coord, check left and right
                               ; For each left and right that's part of box, check their above's left and right
                               ]
                           ; Update current warehouse with boxes and robot in new position
                           [updated-warehouse first-box-pos])
                (= dy -1) (let []
                            ; Update current warehouse with boxes and robot in new position
                            [updated-warehouse first-box-pos]))
          [warehouse robot])))))

(def move-expanded
  (memoize
    (fn [warehouse movement [rx ry]]
      (println "Robot" [rx ry])
      (println "Movement" movement)
      (let [[nx ny] (case movement
                      "^" (utils/top-coord rx ry)
                      ">" (utils/right-coord rx ry)
                      "v" (utils/bottom-coord rx ry)
                      "<" (utils/left-coord rx ry))]
        (case (utils/get-cell-value warehouse [nx ny])
          "." [(->
                 warehouse
                 (assoc-in [ry rx] ".")
                 (assoc-in [ny nx] "@"))
               [nx ny]]
          "#" [warehouse [rx ry]]
          ("[" "]") (move-double-boxes warehouse [rx ry] [nx ny]))))))

(let [[warehouse movements] (input->warehouse-and-movements)
      warehouse (expand-warehouse warehouse)
      _ (utils/draw-grid warehouse)
      _ (println "")
      final-warehouse (loop [warehouse warehouse
                             movements movements
                             robot (utils/value->coord warehouse "@")]
                        (if (empty? movements)
                          warehouse
                          (let [[movement & remaining] movements
                                [updated-warehouse new-robot] (move-expanded warehouse movement robot)]
                            (utils/draw-grid updated-warehouse)
                            (println "")
                            (recur updated-warehouse remaining new-robot))))]
  (utils/draw-grid final-warehouse))

(defn part-2 [] 0)
