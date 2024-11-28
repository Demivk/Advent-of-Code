(ns year2023.day15.puzzles
  (:require
    [clojure.string :as string]))

(def input (slurp (utils/input-file-path 2023 15)))

(defn run-hash-algorithm [step]
  (reduce
    (fn [value character]
      (->
        value
        (+ (int character))
        (* 17)
        (mod 256)))
    0
    (seq step)))

(defn part-1 []
  (->>
    (string/split input #",")
    (mapv run-hash-algorithm)
    (apply +)))

(defn add-lens [box label focal-length]
  (let [existing (first (for [index (range (count box))
                              :when (= label (first (nth box index)))]
                          index))]
    (if existing
      (assoc-in box [existing 1] (utils/parse-int focal-length))
      (conj box [label (utils/parse-int focal-length)]))))

(defn remove-lens [box label] (filterv #(not= label (first %)) box))

(defn change-boxes [box label operation focal-length]
  (if (= operation "=")
    (add-lens box label focal-length)
    (remove-lens box label)))

(defn part-2 []
  (->>
    (string/split input #",")
    (reduce
      (fn [boxes step]
        (let [[_ label operation focal-length] (re-matches #"([a-z]+)([-=])(\d)?" step)]
          (update boxes (run-hash-algorithm label) change-boxes label operation focal-length)))
      (into [] (take 256 (repeat []))))
    (map-indexed
      (fn [box-i box]
        (map-indexed
          (fn [slot-i [_ focal-length]]
            (* (inc box-i) (inc slot-i) focal-length))
          box)))
    flatten
    (apply +)))
