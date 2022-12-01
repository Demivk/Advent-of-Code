(ns day1.day1
  (:require
    [clojure.string :as string]
    [clojure.edn :as edn]))

(defn read-input [] (map #(map edn/read-string (string/split-lines %)) (string/split (slurp "src/day1/input.edn") #"\n\n")))

(defn part-1 []
  (->>
    (read-input)
    (mapv #(reduce + %))
    (apply max)))

(defn part-2 []
  (->>
    (read-input)
    (mapv #(reduce + %))
    (sort)
    (take-last 3)
    (apply +)))
