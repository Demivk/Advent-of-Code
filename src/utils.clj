(ns utils
  (:require
    [clojure.string :as string]))

(defn input-file-path [year day] (str "src/year" year "/day" day "/input.edn"))

(defn read-rows [input] (flatten (mapv #(string/split-lines %) (string/split input #"\n"))))

(defn split-whitespace [s] (string/split s #" "))

; Numbers
(defn num? [s] (some? (first (re-matches #"\d+(\.\d+)?" (str s)))))

(defn parse-int [s] (Integer/parseInt s))

(defn parse-big-int [s] (BigInteger. s))

(defn get-ints
  "Returns a list of all the numbers in s as an int. Includes negative ints."
  [s] (mapv parse-int (re-seq #"-?\d+" s)))

(defn get-big-ints
  "Returns a list of all the numbers in s as a big int. Includes negative ints."
  [s] (mapv parse-big-int (re-seq #"-?\d+" s)))

; Grid
(defn read-grid [input] (mapv #(string/split % #"") (read-rows input)))

(defn get-cell
  ([grid [x y]] (get-cell grid x y))
  ([grid x y] (get-in grid [y x])))

(defn top-coord [x y] [x (dec y)])
(defn right-coord [x y] [(inc x) y])
(defn bottom-coord [x y] [x (inc y)])
(defn left-coord [x y] [(dec x) y])

(defn get-top [grid x y] (when (>= (dec y) 0) (get-in grid [(dec y) x])))
(defn get-right [grid x y] (when (<= (inc x) (count grid)) (get-in grid [y (inc x)])))
(defn get-bottom [grid x y] (when (<= (inc y) (count (first grid))) (get-in grid [(inc y) x])))
(defn get-left [grid x y] (when (>= (dec x) 0) (get-in grid [y (dec x)])))

(defn get-adjacent-positions
  "Returns the values of the top, right, bottom and left cells in that order."
  [grid x y] ((juxt get-top get-right get-bottom get-left) grid x y))

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
