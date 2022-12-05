(ns day4.day4
  (:require
    [clojure.string :as string]
    [clojure.edn :as edn]))

(defn read-input [] (flatten (map #(string/split-lines %) (string/split (slurp "src/day4/input.edn") #"\n\n"))))

(defn filter-fully-overlapping [input]
  (filterv
    (fn [pairs]
     (let [[fs fe ss se] (->>
                           (string/split pairs #",")
                           (mapv #(string/split % #"-"))
                           (flatten)
                           (mapv edn/read-string))]
       (or
           (and (<= fs ss) (>= fe se))
           (and (<= ss fs) (>= se fe)))))
    input))

(defn part-1 []
  (->>
    (read-input)
    (filter-fully-overlapping)
    (count)))

(defn filter-partly-overlapping [input]
  (filterv
    (fn [pairs]
      (let [[fs fe ss se] (->>
                            (string/split pairs #",")
                            (mapv #(string/split % #"-"))
                            (flatten)
                            (mapv edn/read-string))]
        (or
          (and (<= fs se) (>= fs ss))
          (and (<= ss fe) (>= ss fs)))))
    input))

(defn part-2 []
  (->>
    (read-input)
    (filter-partly-overlapping)
    (count)))

(comment
  (part-1)
  (part-2))
