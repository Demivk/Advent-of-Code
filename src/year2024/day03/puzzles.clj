(ns year2024.day03.puzzles)

(def input (slurp (utils/input-file-path 2024 3)))

(defn multiply [instruction]
  (let [[_ x y] (re-find #"(\d{1,3}),(\d{1,3})" instruction)]
    (* (utils/parse-int x) (utils/parse-int y))))

(defn part-1 []
  (->>
    (re-seq #"mul\(\d{1,3},\d{1,3}\)" input)
    (map multiply)
    (apply +)))

(defn remove-disabled-instructions [grouped-instructions]
  (let [without-donts (loop [remaining grouped-instructions
                             instructions []]
                        (let [[to-check & rest] remaining]
                          (if (seq to-check)
                            (if (= (first to-check) "don't()")
                              (recur (drop 1 rest) instructions)
                              (recur rest (into instructions to-check)))
                            instructions)))]
    (filterv #(not= % "do()") without-donts)))

(defn part-2 []
  (->>
    (re-seq #"mul\(\d{1,3},\d{1,3}\)|do\(\)|don't\(\)" input)
    (partition-by #(re-matches #"do\(\)|don't\(\)" %))
    (remove-disabled-instructions)
    (map multiply)
    (apply +)))
