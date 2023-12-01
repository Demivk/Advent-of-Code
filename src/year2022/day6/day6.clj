(ns year2022.day6.day6
  (:require
    [clojure.string :as string]))

(defn read-input [] (string/replace (slurp "src/year2022/day6/input.edn") #"\n" ""))

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
