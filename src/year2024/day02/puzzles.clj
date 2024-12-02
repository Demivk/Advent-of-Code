(ns year2024.day02.puzzles)

(def input (slurp (utils/input-file-path 2024 2)))

(defn levels->pair-diffs [levels]
  (->>
    levels
    (partition 2 1)
    (mapv (fn [[l r]] (- r l)))))

(defn valid? [pair-diffs]
  (and
    (or (every? pos? pair-diffs) (every? neg? pair-diffs))
    (not (some #(or (zero? %) (> % 3) (< % -3)) pair-diffs))))

(defn part-1 []
  (->>
    (utils/split-new-line input)
    (filterv
      (fn [report]
        (let [levels (mapv utils/parse-int (utils/split-whitespace report))
              pair-diffs (levels->pair-diffs levels)]
          (valid? pair-diffs))))
    (count)))

(defn part-2 []
  (->>
    (utils/split-new-line input)
    (filterv
      (fn [report]
        (let [levels (mapv utils/parse-int (utils/split-whitespace report))
              pair-diffs (levels->pair-diffs levels)]
          (or
            (valid? pair-diffs)
            (some
              (fn [x]
                (let [new-levels (vec (concat (subvec levels 0 x) (subvec levels (inc x))))
                      new-pair-diffs (levels->pair-diffs new-levels)]
                  (valid? new-pair-diffs)))
              (range (count levels)))))))
    (count)))
