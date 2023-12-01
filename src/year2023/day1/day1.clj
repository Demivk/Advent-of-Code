(ns year2023.day1.day1
  (:require
    [clojure.edn :as edn]
    [utils :as utils]))

(def input-path "src/year2023/day1/input.edn")

(defn part-1 []
  (->>
    (utils/read-rows input-path)
    (mapv (fn [row] (filterv #(Character/isDigit %) row)))
    (mapv #(edn/read-string (apply str [(first %) (last %)])))
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
      (.replace input word (get word-mapping word)))
    s
    (keys word-mapping)))

(defn part-2 []
  (->>
    (utils/read-rows input-path)
    (mapv #(replace-words %))
    (mapv (fn [row] (filterv #(Character/isDigit %) row)))
    (mapv #(edn/read-string (apply str [(first %) (last %)])))
    (apply +)))
