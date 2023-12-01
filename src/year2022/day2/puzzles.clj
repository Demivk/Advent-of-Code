(ns year2022.day2.puzzles
  (:require
    [clojure.string :as string]))

(defn read-input [] (flatten (map #(string/split-lines %) (string/split (slurp "src/year2022/day2/input.edn") #"\n\n"))))

(def win-inputs #{"A Y" "B Z" "C X"})
(def draw-inputs #{"A X" "B Y" "C Z"})
(def lose-inputs #{"A Z" "B X" "C Y"})

(defn shape->score [shape] (case shape "X" 1 "Y" 2 "Z" 3))

(defn input->part-1-score [input]
  (let [[_ shape] (string/split input #" ")
        shape-score (shape->score shape)
        outcome-score (cond
                        (contains? lose-inputs input) 0
                        (contains? draw-inputs input) 3
                        (contains? win-inputs input) 6)]
    (+ shape-score outcome-score)))

(defn part-1 []
  (->>
    (read-input)
    (mapv #(input->part-1-score %))
    (apply +)))

(defn find-shape [opponent outcome-inputs]
  (some
    #(let [splitted (string/split % #" ")]
       (when (= (first splitted) opponent) (second splitted)))
    outcome-inputs))

(defn input->part-2-score [input]
  (let [[opponent shape] (string/split input #" ")
        shape-score (shape->score
                      (case shape
                        "X" (find-shape opponent lose-inputs)
                        "Y" (find-shape opponent draw-inputs)
                        "Z" (find-shape opponent win-inputs)))
        outcome-score (case shape "X" 0 "Y" 3 "Z" 6)]
    (+ shape-score outcome-score)))

(defn part-2 []
  (->>
    (read-input)
    (mapv #(input->part-2-score %))
    (apply +)))
