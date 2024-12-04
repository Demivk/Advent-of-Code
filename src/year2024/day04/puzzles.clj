(ns year2024.day04.puzzles)

(def input (slurp (utils/input-file-path 2024 4)))

; TODO took and changed from utils, something doesn't work, same with get-cells. Refactor later on.
(defn top-left-coord [x y] [(dec x) (dec y)])
(defn top-coord [x y] [x (dec y)])
(defn top-right-coord [x y] [(inc x) (dec y)])
(defn right-coord [x y] [(inc x) y])
(defn bottom-right-coord [x y] [(inc x) (inc y)])
(defn bottom-coord [x y] [x (inc y)])
(defn bottom-left-coord [x y] [(dec x) (inc y)])
(defn left-coord [x y] [(dec x) y])

(defn get-top-left [grid x y]
  (when (and (>= (dec x) 0) (>= (dec y) 0))
    (get-in grid [(dec x) (dec y)])))

(defn get-top [grid x y]
  (when (>= (dec y) 0)
    (get-in grid [x (dec y)])))

(defn get-top-right [grid x y]
  (when (and (< (inc x) (count (first grid))) (>= (dec y) 0))
    (get-in grid [(inc x) (dec y)])))

(defn get-right [grid x y]
  (when (< (inc x) (count (first grid)))
    (get-in grid [(inc x) y])))

(defn get-bottom-right [grid x y]
  (when (and (< (inc x) (count (first grid))) (< (inc y) (count grid)))
    (get-in grid [(inc x) (inc y)])))

(defn get-bottom [grid x y]
  (when (< (inc y) (count grid))
    (get-in grid [x (inc y)])))

(defn get-bottom-left [grid x y]
  (when (and (>= (dec x) 0) (< (inc y) (count grid)))
    (get-in grid [(dec x) (inc y)])))

(defn get-left [grid x y]
  (when (>= (dec x) 0)
    (get-in grid [(dec x) y])))

(defn get-surrounding-values
  "Returns the values of the top left, top, top right, right, bottom right, bottom, bottom left and left cells in
  that order."
  [grid x y]
  ((juxt get-top-left get-top get-top-right get-right get-bottom-right get-bottom get-bottom-left get-left) grid x y))

(defn get-surrounding-coords
  "Returns the coords of the top left, top, top right, right, bottom right, bottom, bottom left and left cells in
  that order."
  [x y]
  ((juxt top-left-coord top-coord top-right-coord right-coord bottom-right-coord bottom-coord bottom-left-coord left-coord) x y))

(defn part-1 []
  (let [grid (utils/read-grid input)
        hashmap (utils/grid->coords-hashmap grid)
        start-positions (get hashmap "X")]
    (->
      (reduce
        (fn [acc [x y]]
          (let [m-coords (into #{} (get hashmap "M"))
                surrounding-m-coords (filterv #(contains? m-coords %) (get-surrounding-coords x y))
                valid-words (mapv
                              (fn [coord]
                                (let [directions [(- (first coord) x) (- (last coord) y)]
                                      next-coord [(+ (first coord) (first directions)) (+ (last coord) (last directions))]
                                      next-coord-value (get-in grid [(first next-coord) (last next-coord)])]
                                  (when (= next-coord-value "A")
                                    (let [next-next-coord [(+ (first next-coord) (first directions)) (+ (last next-coord) (last directions))]
                                          next-next-coord-value (get-in grid [(first next-next-coord) (last next-next-coord)])]
                                      (= next-next-coord-value "S")))))
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
          (let [[tl _ tr _ br _ bl _] (get-surrounding-values grid x y)
                valid? (or
                         (and (= tl "M") (= br "S") (= tr "M") (= bl "S"))
                         (and (= tl "M") (= br "S") (= tr "S") (= bl "M"))
                         (and (= tl "S") (= br "M") (= tr "M") (= bl "S"))
                         (and (= tl "S") (= br "M") (= tr "S") (= bl "M")))]
            (cond-> acc valid? (conj true))))
        []
        start-positions)
      count)))
