(ns year2022.day3.puzzles
  (:require
    [clojure.string :as string]
    [clojure.set :as set]))

(defn read-input [] (flatten (map #(string/split-lines %) (string/split (slurp "src/year2022/day3/input.edn") #"\n\n"))))

(defn split-in-half [input] (mapv (partial apply str) (partition-all (/ (count input) 2) input)))

(defn find-shared-type-part-1 [input]
  (let [[lh rh] (mapv #(into #{} (string/split % #"")) (split-in-half input))]
    (first (set/intersection lh rh))))

(defn get-prority [input]
  (let [char-number (int (.charAt input 0))]
    (if (and (>= char-number 65) (<= char-number 90))
      (- char-number 38)                                    ; uppercase
      (- char-number 96))))                                 ; lowercase

(defn part-1 []
  (->>
    (read-input)
    (mapv find-shared-type-part-1)
    (mapv #(get-prority %))
    (apply +)))

(defn find-shared-type-part-2 [input]
  (let [[f s t] (mapv #(into #{} (string/split % #"")) input)]
    (first (set/intersection f s t))))

(defn part-2 []
  (->>
    (read-input)
    (partition 3)
    (mapv find-shared-type-part-2)
    (mapv #(get-prority %))
    (apply +)))
