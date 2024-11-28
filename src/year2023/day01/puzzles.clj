(ns year2023.day01.puzzles
  (:require
    [clojure.string :as string]))

(def input (slurp (utils/input-file-path 2023 1)))

(defn part-1 []
  (->>
    (utils/read-rows input)
    (mapv (fn [row] (filterv #(Character/isDigit %) row)))
    (mapv #(utils/parse-int (apply str [(first %) (last %)])))
    (apply +)))

; eighthree (83), twone (21) are also valid...
(def word-mapping
  {"one" "o1e"
   "two" "t2o"
   "three" "t3e"
   "four" "4"
   "five" "5e"
   "six" "6"
   "seven" "7n"
   "eight" "e8t"
   "nine" "9e"})

(defn replace-words [s]
  (reduce
    (fn [input word]
      (string/replace input word (get word-mapping word)))
    s
    (keys word-mapping)))

(defn part-2 []
  (->>
    (utils/read-rows input)
    (mapv #(replace-words %))
    (mapv (fn [row] (filterv #(Character/isDigit %) row)))
    (mapv #(utils/parse-int (apply str [(first %) (last %)])))
    (apply +)))
