(ns utils
  (:require
    [clojure.string :as string]))

(defn input-file-path [year day] (str "src/year" year "/day" day "/input.edn"))

(defn read-rows [input] (flatten (mapv #(string/split-lines %) (string/split input #"\n"))))

(defn read-grid [input] (mapv #(string/split % #"") (read-rows input)))

(defn split-whitespace [s] (string/split s #" "))

(defn num? [s] (some? (first (re-matches #"\d+(\.\d+)?" (str s)))))

(defn parse-int [s] (Integer/parseInt s))

(defn parse-big-int [s] (BigInteger. s))

(defn get-ints [s] (mapv parse-int (re-seq #"\d+" s)))

(defn get-big-ints [s] (mapv parse-big-int (re-seq #"\d+" s)))

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
