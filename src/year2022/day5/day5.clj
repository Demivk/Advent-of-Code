(ns year2022.day5.day5
  (:require
    [clojure.string :as string]
    [clojure.edn :as edn]))

(defn read-input [] (string/split (slurp "src/year2022/day5/input.edn") #"\n\n"))

(def pile-1 ["[V]" "[Q]" "[W]" "[M]" "[B]" "[N]" "[Z]" "[C]"])
(def pile-2 ["[B]" "[C]" "[W]" "[R]" "[Z]" "[H]"])
(def pile-3 ["[J]" "[R]" "[Q]" "[F]"])
(def pile-4 ["[T]" "[M]" "[N]" "[F]" "[H]" "[W]" "[S]" "[Z]"])
(def pile-5 ["[P]" "[Q]" "[N]" "[L]" "[W]" "[F]" "[G]"])
(def pile-6 ["[W]" "[P]" "[L]"])
(def pile-7 ["[J]" "[Q]" "[C]" "[G]" "[R]" "[D]" "[B]" "[V]"])
(def pile-8 ["[W]" "[B]" "[N]" "[Q]" "[Z]"])
(def pile-9 ["[J]" "[T]" "[G]" "[C]" "[F]" "[L]" "[H]"])

(def crates [pile-1 pile-2 pile-3 pile-4 pile-5 pile-6 pile-7 pile-8 pile-9])

(defn follow-procedure [reverse? procedures]
  (let [procedures (mapv #(mapv edn/read-string (string/split % #" ")) (string/split procedures #"\n"))]
    (reduce
      (fn [crates [_ amount _ from _ to]]
        (let [to-move (vec (take amount (nth crates (dec from))))
              new-from (vec (drop amount (nth crates (dec from))))
              new-to (into (if reverse? (vec (reverse to-move)) to-move) (nth crates (dec to)))]
          (->
            crates
            (assoc (dec from) new-from)
            (assoc (dec to) new-to))))
      crates
      procedures)))

(defn read-top-crates [crates]
  (->>
    crates
    (mapv first)
    (mapv #(string/replace % #"[\[ \]]" ""))
    (string/join)))

(defn part-1 []
  (->>
    (second (read-input))
    (follow-procedure true)
    (read-top-crates)))

(defn part-2 []
  (->>
    (second (read-input))
    (follow-procedure false)
    (read-top-crates)))
