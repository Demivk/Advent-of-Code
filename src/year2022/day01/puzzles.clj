(ns year2022.day01.puzzles
  (:require
    [clojure.string :as string]
    [clojure.edn :as edn]))

(defn read-input [] (map #(map edn/read-string (string/split-lines %)) (string/split (slurp (utils/input-file-path 2022 1)) #"\n\n")))

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
