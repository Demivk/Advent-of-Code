(ns day10.day10
  (:require
    [clojure.string :as string]
    [clojure.edn :as edn]))

(defn read-input [] (string/split (slurp "src/day10/input.edn") #"\n"))

(defn execute-program [instructions]
  (loop [[instruction & rest] instructions
         x 1
         cycle [x]]
    (if (nil? instruction)
      cycle
      (if (= instruction "noop")
        (recur rest x (conj cycle x))
        (let [[_ value] (mapv edn/read-string (string/split instruction #" "))]
          (recur rest (+ x value) (into cycle [x (+ x value)])))))))

(defn signal-strengths [cycles]
  (reduce
    (fn [result n]
      (let [value (nth cycles (dec n))]
        (conj result (* value n))))
    []
    [20 60 100 140 180 220]))

(defn part-1 []
  (->>
    (read-input)
    (execute-program)
    (signal-strengths)
    (reduce +)))

(defn draw-sprite [cycles]
  (mapv
    (fn [first-pixel]
      (string/join
        ""
        (map-indexed
          (fn [i n]
            (let [x (nth cycles (dec n))]
              (if (some #(= i %) [(dec x) x (inc x)])
                "#"
                ".")))
          (range first-pixel (+ first-pixel 40)))))
    [1 41 81 121 161 201]))

(defn part-2 []
  ; Split vertically per 5 to read each letter
  (->>
    (read-input)
    (execute-program)
    (draw-sprite)))
