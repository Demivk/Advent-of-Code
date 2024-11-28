(ns year2022.day06.puzzles
  (:require
    [clojure.string :as string]))

(defn read-input [] (string/replace (slurp (utils/input-file-path 2022 6)) #"\n" ""))

(defn get-start-marker [size partitioned]
  (loop [[r & rest] (mapv #(into #{} %) partitioned)
         index 0]
    (if (= (count r) size)
      (+ index size)
      (recur rest (inc index)))))

(defn part-1 []
  (->>
    (read-input)
    (partition-all 4 1)
    (get-start-marker 4)))

(defn part-2 []
  (->>
    (read-input)
    (partition-all 14 1)
    (get-start-marker 14)))
