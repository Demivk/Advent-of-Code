(ns year2024.day07.puzzles
  (:require
    [clojure.string :as string]))

(def input (slurp (utils/input-file-path 2024 7)))

(defn input->equations []
  (map
    #(let [[test-value configurations] (string/split % #": ")]
       [(utils/parse-big-int test-value) (mapv utils/parse-big-int (utils/split-whitespace configurations))])
    (utils/split-new-line input)))

(defn calculate
  "For each number in configurations, applies +, * and optionally combine the current number to the
  previously calculated values.

  Example: [10 19]
  1. Handle 10
  2. [] is empty, so [10]
  3. Handle 19
  4. Mapcat [(* 10 19) (+ 10 19) 1019] = [190 29 1019]
  5. Result: (190 29 1019)

  Example: [81 40 27]
  1. Handle 81
  2. [] is empty, so [81]
  3. Handle 40
  4. Mapcat results in [(* 81 40) (+ 81 40) 8140] = [3240 121 8410]
  5. Handle 27
  6. Mapcat
     [(* 3240 27 ) (+ 3240 27 ) 324027] = [87480 3267 324027]
     [(* 121 27 ) (+ 121 27 ) 12127] = [3267 148 12127]
     [(* 8140 27 ) (+ 8140 27 ) 814027] = [219780 8167 814027]
  7. Result: (87480 3267 324027 3267 148 12127 219780 8167 814027)"
  [configurations combine?]
  (reduce
    (fn [applied-calculations n]
      (if (empty? applied-calculations)
        [n]
        (mapcat
          (fn [current]
            (cond-> [(* current n) (+ current n)]
              combine? (conj (utils/parse-big-int (str current n)))))
          applied-calculations)))
    []
    configurations))

(defn part-1 []
  (->>
    (input->equations)
    (keep
      (fn [[test-value configurations]]
        (when (some #(= % test-value) (calculate configurations false))
          test-value)))
    (apply +)))

(defn part-2 []
  (->>
    (input->equations)
    (keep
      (fn [[test-value configurations]]
        (when (some #(= % test-value) (calculate configurations true))
          test-value)))
    (apply +)))
