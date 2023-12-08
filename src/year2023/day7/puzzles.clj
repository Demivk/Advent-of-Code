(ns year2023.day7.puzzles
  (:require
    [clojure.string :as string]
    [utils :as utils]))

(def input (slurp (utils/input-file-path 2023 7)))

(def cards-part-1 "23456789TJQKA")
(def cards-part-2 "J23456789TQKA")

(def five-of-a-kind-strength 7)
(def four-of-a-kind-strength 6)
(def full-house-strength 5)
(def three-of-a-kind-strength 4)
(def two-pair-strength 3)
(def one-pair-strength 2)
(def high-card-strength 1)

(defn joker-freqs [cards]
  (let [card-list (string/split cards #"")
        joker-count (count (filterv #(= % "J") card-list))
        non-joker-freqs (dissoc (frequencies cards) \J)]
    (if (= joker-count 5)
      '(5)
      (let [highest-count-key (first (apply max-key val non-joker-freqs))
            non-joker-freqs (update non-joker-freqs highest-count-key #(+ % joker-count))]
        (sort > (vals non-joker-freqs))))))

(defn hand-strength [cards part]
  (let [freqs (if (= part 1) (sort > (vals (frequencies cards))) (joker-freqs cards))]
    (condp = freqs
      '(5) five-of-a-kind-strength
      '(4 1) four-of-a-kind-strength
      '(3 2) full-house-strength
      '(3 1 1) three-of-a-kind-strength
      '(2 2 1) two-pair-strength
      '(2 1 1 1) one-pair-strength
      '(1 1 1 1 1) high-card-strength
      0)))

(defn card-values [part] (into {} (map-indexed (fn [i card] {card i}) (if (= part 1) cards-part-1 cards-part-2))))

(defn compare-hands [hand-1 hand-2 part]
  (let [hand-1-strength (hand-strength hand-1 part)
        hand-2-strength (hand-strength hand-2 part)]
    (if (not= hand-1-strength hand-2-strength)
      (compare hand-1-strength hand-2-strength)
      (loop [[[f s] & remaining] (mapv vector hand-1 hand-2)]
        (let [result (compare (get (card-values part) f) (get (card-values part) s))]
          (if (zero? result) (recur remaining) result))))))

(defn part-1 []
  (let [hands (->>
                (utils/read-rows input)
                (mapv
                  #(let [[cards bid] (utils/split-whitespace %)]
                     {cards (utils/parse-int bid)}))
                (into {}))
        sorted-hands (sort #(compare-hands %1 %2 1) (keys hands))]
    (->>
      sorted-hands
      (map-indexed (fn [i hand] (let [rank (inc i)] (* rank (get hands hand)))))
      (apply +))))

(defn part-2 []
  (let [hands (->>
                (utils/read-rows input)
                (mapv
                  #(let [[cards bid] (utils/split-whitespace %)]
                     {cards (utils/parse-int bid)}))
                (into {}))
        sorted-hands (sort #(compare-hands %1 %2 2) (keys hands))]
    (->>
      sorted-hands
      (map-indexed (fn [i hand] (let [rank (inc i)] (* rank (get hands hand)))))
      (apply +))))
