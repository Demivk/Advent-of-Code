(ns year2022.day4.puzzles
  (:require
    [clojure.string :as string]
    [clojure.edn :as edn]))

(defn read-input [] (flatten (map #(string/split-lines %) (string/split (slurp "src/year2022/day4/input.edn") #"\n\n"))))

(defn filter-overlapping [fully? input]
  (filterv
    (fn [pairs]
      (let [[fs fe ss se] (->>
                            (string/split pairs #",")
                            (mapv #(string/split % #"-"))
                            (flatten)
                            (mapv edn/read-string))]
        (if fully?
          (or
            (and (<= fs ss) (>= fe se))
            (and (<= ss fs) (>= se fe)))
          (or
            (and (<= fs se) (>= fs ss))
            (and (<= ss fe) (>= ss fs))))))
    input))

(defn part-1 []
  (->>
    (read-input)
    (filter-overlapping true)
    (count)))

(defn part-2 []
  (->>
    (read-input)
    (filter-overlapping false)
    (count)))

(comment
  (part-1)
  (part-2))
