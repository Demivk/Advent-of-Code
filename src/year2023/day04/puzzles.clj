(ns year2023.day04.puzzles
  (:require
    [clojure.math :as math]
    [clojure.set :as set]
    [clojure.string :as string]))

(def input (slurp (utils/input-file-path 2023 4)))

(defn get-matches [winning-numbers your-numbers] (set/intersection (into #{} winning-numbers) (into #{} your-numbers)))

(defn map-input [part-1?]
  (->>
    (utils/read-rows input)
    (mapv
      (fn [row]
        (let [[card numbers] (string/split row #": ")
              card-number (first (utils/get-ints card))
              [winning-numbers your-numbers] (mapv #(utils/get-ints %) (string/split numbers #" \| "))
              matches (get-matches winning-numbers your-numbers)]
          (if part-1?
            (int (math/pow 2 (dec (count matches))))
            {card-number [(vec matches)]}))))))

(defn part-1 [] (apply + (map-input true)))

(defn part-2 []
  (let [new-cards
        (loop [cards (map-input false)
               i 0]
          (if (>= i (count cards))
            cards
            (recur
              (let [card (get cards i)
                    card-number (inc i)
                    matches (get card card-number)
                    card-indexes-to-update (range card-number (+ card-number (count (first matches))))]
                (reduce
                  (fn [acc card-index]
                    (update acc card-index
                      (fn [card]
                        (let [card-number (inc card-index)
                              existing-matches (get card card-number)
                              first-match (first existing-matches)]
                          {card-number (into existing-matches (vec (repeat (count matches) first-match)))}))))
                  cards
                  card-indexes-to-update))
              (inc i))))]
    (->>
      new-cards
      (map-indexed (fn [i card] (count (get card (inc i)))))
      (apply +))))
