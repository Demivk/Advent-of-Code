(ns year2025.day06.puzzles
  (:require
   [clojure.string :as string]
   [utils]))

(def input (slurp (utils/input-file-path 2025 6)))

(defn part-1 []
  (reduce
   (fn [total problem]
     (let [operation (resolve (symbol (last problem)))
           numbers (mapv utils/parse-long (butlast problem))]
       (+ total (apply operation numbers))))
   0
   (utils/transpose-grid (utils/read-grid input #"\s+"))))

(defn part-2 []
  (let [grid (utils/read-grid input)
        padded-grid (mapv #(into % (repeat (- (apply max (map count grid)) (count %)) " ")) grid)
        columns-rtl (->>
                      (utils/transpose-grid padded-grid)
                      (partition-by (fn [problem] (every? #(= " " %) problem)))
                      (remove (fn [column] (every? #(= " " %) (first column))))
                      (mapv utils/transpose-grid))]
    (reduce
     (fn [total columns]
       (let [transposed (utils/transpose-grid columns)
             operation (resolve (symbol (last (first transposed))))
             numbers (mapv #(utils/parse-long (string/trim (apply str (butlast %)))) transposed)]
         (+ total (apply operation numbers))))
     0
     columns-rtl)))
