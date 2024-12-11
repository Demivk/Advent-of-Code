(ns year2024.day11.puzzles)

(def input (slurp (utils/input-file-path 2024 11)))

(defn parse-input [] (mapv utils/parse-int (utils/split-whitespace (first (utils/split-new-line input)))))

(defn split-number [stone]
  (let [s (str stone)
        half-length (/ (count s) 2)]
    [(subs s 0 half-length) (subs s half-length)]))

(def blink
  (memoize                                                  ; thanks Reddit
    (fn [times stone]
      (cond
        (zero? times) 1                                     ; done
        (zero? stone) (blink (dec times) 1)
        (even? (count (str stone)))
        (->>
          (split-number stone)
          (map #(blink (dec times) (utils/parse-int %)))
          (apply +))
        :else (blink (dec times) (* stone 2024))))))

(defn part-1 []
  (->>
    (parse-input)
    (map (partial blink 25))
    (apply +)))

(defn part-2 []
  (->>
    (parse-input)
    (map (partial blink 75))
    (apply +)))

; Initial part-1: brute force
(comment
  (let [stones (parse-input)
        blink (fn [stones]
                (mapcat
                  (fn [stone]
                    (cond
                      (zero? stone) [1]
                      (even? (count (str stone)))
                        (let [half-length (/ (count (str stone)) 2)]
                          (map
                            utils/parse-int
                            [(subs (str stone) 0 half-length)
                             (subs (str stone) half-length)]))
                      :else [(* stone 2024)]))
                  stones))]
    (->>
      (reduce (fn [stones _] (blink stones)) stones (range 25))
      (count))))
