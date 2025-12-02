(ns utils
  (:require
    [clojure.string :as string]))

; String split
(defn split-whitespace [s] (string/split s #" "))

(defn split-all-whitespaces [s] (string/split s #"\s+"))

(defn split-every-character [s] (string/split s #""))

(defn split-new-line [s] (string/split s #"\n"))

(defn split-double-new-lines [s] (string/split s #"\n\n"))

; Reading
(defn input-file-path [year day] (str "src/year" year "/day" (if (< day 10) (str "0" day) day) "/input.edn"))

(defn read-rows [input] (vec (flatten (mapv #(string/split-lines %) (split-new-line input)))))

; Numbers
(defn num? [s] (some? (first (re-matches #"\d+(\.\d+)?" (str s)))))

(defn parse-int [s] (Integer/parseInt s))

(defn parse-big-int [s] (BigInteger. s))

(defn parse-long [s] (Long/parseLong s))

(defn get-ints
  "Returns a list of all the numbers in s as an int. Includes negative ints."
  [s] (mapv parse-int (re-seq #"-?\d+" s)))

(defn get-big-ints
  "Returns a list of all the numbers in s as a big int. Includes negative ints."
  [s] (mapv parse-big-int (re-seq #"-?\d+" s)))

; Grid
(defn read-grid [input] (mapv #(string/split % #"") (read-rows input)))

(defn make-grid [width height]
  (reduce
    (fn [grid y]
      (conj grid (mapv (fn [x] [x y]) (range width))))
    []
    (range height)))

(defn in-bounds? [grid [x y]]
  (and
    (>= x 0) (< x (count (first grid)))
    (>= y 0) (< y (count grid))))

(defn grid->all-coords [grid]
  (for [x (range (count (first grid)))
        y (range (count grid))]
    [x y]))

(defn delta-x
  "Returns the difference of x2 - x1"
  [[x1 _] [x2 _]] (- x2 x1))

(defn delta-y
  "Returns the difference of y2 - y1"
  [[_ y1] [_ y2]] (- y2 y1))

(defn delta
  "Returns the difference of x2 - x1 and y2 - y1"
  [coord-1 coord-2] [(delta-x coord-1 coord-2) (delta-y coord-1 coord-2)])

(defn add-delta [[x y] [dx dy]] [(+ x dx) (+ y dy)])

(defn subtract-delta [[x y] [dx dy]] [(- x dx) (- y dy)])

(defn get-cell-value
  ([grid [x y]] (get-cell-value grid x y))
  ([grid x y] (get-in grid [y x])))

(defn value->coord
  "Returns the coord of the first match in the grid"
  [grid value]
  (some
    (fn [[i row]]
      (when (some #(= % value) row)
        [(.indexOf row value) i]))
    (map-indexed vector grid)))

(defn values->coords
  "Returns the coords of all matches in the grid"
  [grid value]
  (->>
    (keep
      (fn [[y row]]
        (seq (keep
               (fn [[x v]] (when (= v value) [x y]))
               (map-indexed vector row))))
      (map-indexed vector grid))
    (mapcat identity)))

(defn draw-grid [grid]
  (println
    (->>
      (for [y (range (count grid))]
        (->>
          (for [x (range (count (first grid)))] (get-cell-value grid x y))
          (apply str)))
      (clojure.string/join "\n"))))

(defn draw-coords-on-grid
  ([grid coords] (draw-coords-on-grid (count (first grid)) (count grid) coords))
  ([width height coords]
   (println
     (->>
       (for [y (range height)]
         (->>
           (for [x (range width)] (if (contains? (into #{} coords) [x y]) "#" "."))
           (apply str)))
       (clojure.string/join "\n")))))

(defn top-left-coord [x y] [(dec x) (dec y)])
(defn top-coord [x y] [x (dec y)])
(defn top-right-coord [x y] [(inc x) (dec y)])
(defn right-coord [x y] [(inc x) y])
(defn bottom-right-coord [x y] [(inc x) (inc y)])
(defn bottom-coord [x y] [x (inc y)])
(defn bottom-left-coord [x y] [(dec x) (inc y)])
(defn left-coord [x y] [(dec x) y])

(defn top-left-value [grid x y]
  (when (and (>= (dec x) 0) (>= (dec y) 0))
    (get-cell-value grid (dec x) (dec y))))

(defn top-value [grid x y]
  (when (>= (dec y) 0)
    (get-cell-value grid x (dec y))))

(defn top-right-value [grid x y]
  (when (and (< (inc x) (count (first grid))) (>= (dec y) 0))
    (get-cell-value grid (inc x) (dec y))))

(defn right-value [grid x y]
  (when (< (inc x) (count (first grid)))
    (get-cell-value grid (inc x) y)))

(defn bottom-right-value [grid x y]
  (when (and (< (inc x) (count (first grid))) (< (inc y) (count grid)))
    (get-cell-value grid (inc x) (inc y))))

(defn bottom-value [grid x y]
  (when (< (inc y) (count grid))
    (get-cell-value grid x (inc y))))

(defn bottom-left-value [grid x y]
  (when (and (>= (dec x) 0) (< (inc y) (count grid)))
    (get-cell-value grid (dec x) (inc y))))

(defn left-value [grid x y]
  (when (>= (dec x) 0)
    (get-cell-value grid (dec x) y)))

(defn get-cardinal-coords
  "Returns the coords of the top, right, bottom and left cells in that order."
  [x y]
  ((juxt top-coord right-coord bottom-coord left-coord) x y))

(defn get-cardinal-values
  "Returns the values of the top, right, bottom and left cells in that order."
  [grid x y] ((juxt top-value right-value bottom-value left-value) grid x y))

(defn get-all-surrounding-coords
  "Returns the coords of the top left, top, top right, right, bottom right, bottom, bottom left and left cells in
  that order."
  [x y]
  ((juxt top-left-coord top-coord top-right-coord right-coord bottom-right-coord bottom-coord bottom-left-coord left-coord) x y))

(defn get-all-surrounding-values
  "Returns the values of the top left, top, top right, right, bottom right, bottom, bottom left and left cells in
  that order."
  [grid x y]
  ((juxt top-left-value top-value top-right-value right-value bottom-right-value bottom-value bottom-left-value left-value) grid x y))

(defn transpose-grid "Switches the rows and columns of a grid"
  [grid] (apply mapv vector grid))

; Hashmap
(defn grid->coords-hashmap
  "Returns a map where the keys are the cells of the grid and the values their coords."
  [grid]
  (reduce
    (fn [hashmap [row-i row]]
      (reduce
        (fn [hashmap [col-i val]]
          (update hashmap val (fn [coords] (conj (or coords []) [col-i row-i]))))
        hashmap
        (map-indexed vector row)))
    {}
    (map-indexed vector grid)))

; BFS Breadth-First Search
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

; GCD / LCM
(defn gcd
  "GCD (Greatest Common Divisor)

  Examples:
  (gcd 6 10) => 30
  (gcd 36 18) => 18
  (gcd 0 2) => 2"
  [a b]
  (if (zero? b)
    a
    (recur b (mod a b))))

(defn lcm
  "LCM (Least Common Multiple)

  Examples:
  (lcm 6 10) => 30
  (lcm 36 18) => 36
  (lcm 0 2) => 0"
  [a b]
  (if (or (zero? a) (zero? b))
    0
    (/ (abs (* a b)) (gcd a b))))

(defn lcm-seq
  "LCM for more than two numbers

  Examples:
  (lcm-seq 6 10 4) => 210
  (lcm-seq 36 18 72) => 72
  (lcm-seq 0 2 4) => 0"
  [& numbers] (reduce lcm numbers))
