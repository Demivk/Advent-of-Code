(ns year2024.day13.puzzles)

(def input (slurp (utils/input-file-path 2024 13)))

(defn calculate-tokens [higher-conversion?]
  (->>
    (utils/split-double-new-lines input)
    (keep
      (fn [machine]
        (let [[a b prize] (utils/split-new-line machine)
              [xa ya] (utils/get-ints a)
              [xb yb] (utils/get-ints b)
              [prize-x prize-y] (cond->> (utils/get-ints prize)
                                  higher-conversion? (map #(+ % 10000000000000)))
              a (/ (- (* prize-y xb) (* prize-x yb)) (- (* ya xb) (* xa yb)))
              b (/ (- (* prize-y xa) (* ya prize-x)) (- (* yb xa) (* ya xb)))]
          (when
            (and
              (int? a)
              (int? b)
              (= (apply + [(* xa a) (* xb b)]) prize-x)
              (= (apply + [(* ya a) (* yb b)]) prize-y))
            (+ (* a 3) (* b 1))))))
    (apply +)))

(defn part-1 [] (calculate-tokens false))

(defn part-2 [] (calculate-tokens true))
