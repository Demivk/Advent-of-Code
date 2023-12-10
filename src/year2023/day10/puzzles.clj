(ns year2023.day10.puzzles
  (:require
    [utils :as utils]))

(def input (slurp (utils/input-file-path 2023 10)))

(def connected-top-pipes #{"|" "7" "F"})
(def connected-right-pipes #{"-" "J" "7"})
(def connected-bottom-pipes #{"|" "L" "J"})
(def connected-left-pipes #{"-" "L" "F"})

(defn get-adjacents [grid [x y]]
  (let [[top-pipe right-pipe bottom-pipe left-pipe] (utils/get-adjacent-positions grid x y)
        top-connected? (contains? connected-top-pipes top-pipe)
        right-connected? (contains? connected-right-pipes right-pipe)
        bottom-connected? (contains? connected-bottom-pipes bottom-pipe)
        left-connected? (contains? connected-left-pipes left-pipe)]
    (cond-> []
      top-connected? (conj (utils/top-coord x y))
      right-connected? (conj (utils/right-coord x y))
      bottom-connected? (conj (utils/bottom-coord x y))
      left-connected? (conj (utils/left-coord x y)))))

(defn next-pipe [grid [x y] dx dy]
  (let [pipe (utils/get-cell grid [x y])]
    (case pipe
      "|" [x (+ y dy)]
      "-" [(+ x dx) y]
      "L" [(+ x dy) (+ y dx)]
      "J" [(- x dy) (- y dx)]
      "7" [(+ x dy) (+ y dx)]
      "F" [(- x dy) (- y dx)]
      "S" [x y])))

(defn get-direction-coords [last-coord current-coord]
  [(- (first current-coord) (first last-coord)) (- (second current-coord) (second last-coord))])

(defn pipes-path [grid path]
  (let [[x y] (get-direction-coords (last (butlast path)) (last path))]
    (if (and (= 0 x) (= 0 y))                               ; back at start
      path
      (recur grid (conj path (next-pipe grid (last path) x y))))))

(defn part-1 []
  (let [grid (utils/read-grid input)
        start-coord (loop [i 0]
                      (let [row (nth grid i)
                            x (.indexOf row "S")]
                        (if-not (neg? x) [x i] (recur (inc i)))))
        start-directions (get-adjacents grid start-coord)]
    (/
      (->
        #{}
        (into (pipes-path grid [start-coord (first start-directions)]))
        (into (pipes-path grid [start-coord (second start-directions)]))
        count)
      2)))
