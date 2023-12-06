(ns year2023.day6.puzzles
  (:require
    [utils :as utils]))

(def input (slurp (utils/input-file-path 2023 6)))

(defn winning-distances [hold-time best-distance]
  (loop [winning-distances []
         t 0]
    (if (> t hold-time)
      winning-distances
      (recur
        (let [speed t
              remaining-time (- hold-time speed)
              distance (* remaining-time speed)]
          (cond-> winning-distances
            (> distance best-distance) (conj distance)))
        (inc t)))))

(defn part-1 []
  (let [[hold-times best-distances] (mapv utils/get-ints (utils/read-rows input))]
    (->>
      best-distances
      (map-indexed (fn [i distance] (winning-distances (nth hold-times i) distance)))
      (mapv count)
      (apply *))))

(defn part-2 []
  (let [[hold-time distance] (mapv
                               #(->>
                                  (utils/get-big-ints %)
                                  (apply str)
                                  utils/parse-big-int)
                               (utils/read-rows input))]
    (count (winning-distances hold-time distance))))
