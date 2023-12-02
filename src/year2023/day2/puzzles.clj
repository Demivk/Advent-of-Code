(ns year2023.day2.puzzles
  (:require
    [clojure.string :as string]
    [utils :as utils]))

(def input (slurp (utils/input-file-path 2023 2)))

(def red "red")
(def green "green")
(def blue "blue")

(defn transform-subsets [subsets]
  (flatten
    (mapv
      (fn [subset]
        (mapv
          (fn [cube]
            (let [[amount colour] (utils/split-whitespace cube)]
              {colour (utils/parse-int amount)}))
          (string/split subset #", ")))
      (string/split subsets #"; "))))

(defn subsets->colour-amount [subsets colour]
  (->>
    subsets
    (filterv #(get % colour))
    (mapv #(get % colour))))

(defn validate-colour-amount [subsets colour]
  (->>
    (subsets->colour-amount subsets colour)
    (mapv
      #(<=
         %
         (condp = colour
           red 12
           green 13
           blue 14)))
    (every? true?)))

(defn part-1 []
  (->>
    (utils/read-rows input)
    (keep
      (fn [row]
        (let [[game subsets] (string/split row #": ")
              game (utils/parse-int (last (utils/split-whitespace game)))
              subsets (transform-subsets subsets)]
          (when (every? true? (mapv #(validate-colour-amount subsets %) [red green blue])) game))))
    (apply +)))

(defn required-colour-amount [subsets colour]
  (apply max (subsets->colour-amount subsets colour)))

(defn part-2 []
  (->>
    (utils/read-rows input)
    (mapv
      (fn [row]
        (let [[_ subsets] (string/split row #": ")
              subsets (transform-subsets subsets)]
          (apply * (mapv #(required-colour-amount subsets %) [red green blue])))))
    (apply +)))
