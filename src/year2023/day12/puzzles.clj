(ns year2023.day12.puzzles
  (:require
    [clojure.math.combinatorics :as comb]
    [clojure.string :as string]))

(def input (slurp (utils/input-file-path 2023 12)))

(defn unfold-springs
  "Example:
  .# => .#?.#?.#?.#?.#"
  [springs] (str springs "?" (apply str (repeat 4 (str "?" springs)))))

(defn map-records
  "Maps the input to a format like [\"#.#.###\" [1 1 3]]. For part two
  it unfolds the springs."
  [rows part]
  (mapv
    (fn [row]
      (let [[springs groups] (utils/split-whitespace row)
            groups (utils/get-ints groups)]
        ; Unfold for part 2
        [(cond-> springs (= part 2) (unfold-springs)) groups]))
    rows))

(defn create-possibilities
  "Returns a vector containing all possible NON-VALIDATED springs
  where the unknowns (= ?'s) have been replaced by every symbol in
  the possibility.

  First checks how many unknowns need to be replaced. By using the
  function comb/selections, creates all posibilities using . and #
  with a length of unknown count.

  It then rebuilds the spring by replacing every unknown one by one
  with every symbol in the possible combination, which is then put
  into a vector along with the groups."
  [springs groups]
  (let [unknown-count (count (filterv #(= % \?) springs))]
    (reduce
      (fn [acc replacements]
        (conj acc
          [(reduce
             (fn [new-springs sym]
               (string/replace-first new-springs #"\?" (str sym)))
             springs
             replacements)
           groups]))
      []
      (comb/selections [\. \#] unknown-count))))

; (comb/selections [\. \#] 2)
; => ((\. \.) (\. \#) (\# \.) (\# \#))

(defn validate-combination
  "Check if the amount of #'s is equal to the groups by splitting the
  springs at the .'s.

  Examples:
  [\"#.#.###\" [1 1 3]]
  (= [1 1 3] [1 1 3]) => true

  [\"##..###\" [1 1 3]]
  (= [2 3] [1 1 3]) => false"
  [[springs groups]]
  (let [springs-counts (->>
                         (string/split springs #"\.")
                         (keep #(when-not (string/blank? %) (count %)))
                         vec)]
    (= springs-counts groups)))

(defn part-1 []
  (->>
    (map-records (utils/read-rows input) 1)
    (mapv
      (fn [[springs groups]]
        (->>
          (create-possibilities springs groups)
          (filterv validate-combination)
          count)))
    (apply +)))

(defn part-2 []
  "The test input already takes way too long to run..."
  #_(->>
      (map-records (utils/read-rows input) 2)
      (mapv
        (fn [[springs groups]]
          (->>
            (create-possibilities springs groups)
            (filterv validate-combination)
            count)))
      (apply +)))
