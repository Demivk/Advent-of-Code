(ns year2024.day09.puzzles)

(def input (slurp (utils/input-file-path 2024 9)))

(defn input->blocks []
  (->>
    (utils/split-every-character input)
    (filterv #(not= % "\n"))
    (mapv utils/parse-int)
    (map-indexed vector)
    (reduce
      (fn [[acc id] [original-i disk]]
        (if (even? original-i)
          [(into acc (repeat disk id)) (inc id)]
          [(into acc (repeat disk ".")) id]))
      [[] 0])
    (first)))

(defn drop-dots [coll]
  (if (and (not (empty? coll)) (= (last coll) "."))
    (recur (butlast coll))
    coll))

(defn part-1 []
  (->>
    (loop [remaining (input->blocks)
           result []]
      (if (empty? remaining)
        result
        (let [[to-check & rest] remaining]
          (if (= to-check ".")
            (if (empty? rest)
              result
              (let [to-move (last rest)]
                (if (= to-move ".")
                  (recur (into [to-move] (drop-dots rest)) result)
                  (recur (butlast rest) (conj result to-move)))))
            (recur rest (conj result to-check))))))
    (map-indexed (fn [i block] (* i block)))
    (apply +)))

(defn part-2 [] 0)

(comment
  (->>
    (loop [remaining (input->blocks)
           result []]
      (if (empty? remaining)
        result
        (let [[to-check & rest] remaining]
          (if (= to-check ".")
            (if (empty? rest)
              result
              (let [to-move (last rest)]
                (if (= to-move ".")
                  (recur (into [to-move] (drop-dots rest)) result)
                  (recur (butlast rest) (conj result to-move)))))
            (recur rest (conj result to-check))))))
    (map-indexed (fn [i block] (* i block)))
    (apply +))

  ;(let [grouped-blocks (partition-by identity (input->blocks))]
  ;  (->>
  ;    (loop [remaining grouped-blocks
  ;           result [[] []]]
  ;      (println (first result) remaining (last result))
  ;      (if (empty? remaining)
  ;        (apply concat result)
  ;        (let [[to-check & rest] remaining]
  ;          (if (= (first to-check) ".")
  ;            (let [to-move (last rest)]
  ;              (if (= (first to-move) ".")
  ;                (recur (butlast rest) [(first result) (into [to-move] (last result))])
  ;                (if (<= (count to-move) (count to-check))
  ;                  (recur
  ;                    (butlast (concat (drop (count to-move) (vec to-check)) remaining))
  ;                    [(conj (first result) to-move) (last result)])
  ;                  (recur
  ;                    (butlast rest)
  ;                    [(first result) (into to-move (last result))]))))
  ;            (recur (drop 1 remaining) [(conj (first result) to-check) (last result)])))))
  ;    (flatten)
  ;    (filterv #(not= % "."))
  ;    (map-indexed (fn [i block] (if block (* i block) 0)))
  ;    (apply +)))

  ;00...111...2...333.44.5555.6666.777.888899
  ;0099.111...2...333.44.5555.6666.777.8888..
  ;0099.1117772...333.44.5555.6666.....8888..
  ;0099.111777244.333....5555.6666.....8888..
  ;00992111777.44.333....5555.6666.....8888..
  )
