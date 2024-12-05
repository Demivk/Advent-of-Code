(ns year2024.day05.puzzles
  (:require
    [clojure.string :as string]))

(def input (slurp (utils/input-file-path 2024 5)))

(defn part-1 []
  (let [[rules production-seq] (utils/split-double-new-lines input)
        rules (->>
                (utils/split-new-line rules)
                (mapv #(mapv utils/parse-int (string/split % #"\|"))))
        production-seq (->>
                         (utils/split-new-line production-seq)
                         (mapv #(mapv utils/parse-int (string/split % #","))))
        correct-updates (keep
                          (fn [pages]
                            (let [rules-for-update (filterv #(every? (into #{} pages) %) rules)
                                  right-order? (loop [remaining pages
                                                      rules rules-for-update]
                                                 (let [[to-check & rest] remaining
                                                       correct? (not (some (fn [[_ y]] (= y to-check)) rules))]
                                                   (if correct?
                                                     (if (seq rest)
                                                       (recur rest (filterv (fn [[x _]] (not= x to-check)) rules))
                                                       true)
                                                     false)))]
                              (when right-order? pages)))
                          production-seq)]
    (->>
      correct-updates
      (mapv #(nth % (quot (count %) 2)))
      (apply +))))

(defn part-2 []
  (let [[rules production-seq] (utils/split-double-new-lines input)
        rules (->>
                (utils/split-new-line rules)
                (mapv #(mapv utils/parse-int (string/split % #"\|"))))
        production-seq (->>
                         (utils/split-new-line production-seq)
                         (mapv #(mapv utils/parse-int (string/split % #","))))
        incorrect-updates (keep
                            (fn [pages]
                              (let [rules-for-update (filterv #(every? (into #{} pages) %) rules)
                                    incorrect-order? (loop [remaining pages
                                                            rules rules-for-update]
                                                       (let [[to-check & rest] remaining
                                                             incorrect? (some (fn [[_ y]] (= y to-check)) rules)]
                                                         (if incorrect?
                                                           true
                                                           (if (seq rest)
                                                             (recur rest (filterv (fn [[x _]] (not= x to-check)) rules))
                                                             false))))]
                                (when incorrect-order? pages)))
                            production-seq)
        updated-updates (mapv
                          (fn [pages]
                            (let [rules-for-update (filterv #(every? (into #{} pages) %) rules)
                                  updated-pages (loop [remaining pages
                                                       rules rules-for-update
                                                       new-updates []]
                                                  (let [[to-check & rest] remaining
                                                        incorrect-x (some (fn [[x y]] (when (= y to-check) x)) rules)]
                                                    (if incorrect-x
                                                      (let [without-incorrect-x (remove #(= % incorrect-x) remaining)
                                                            before (take-while #(not= % to-check) without-incorrect-x)
                                                            after (drop-while #(not= % to-check) without-incorrect-x)
                                                            new-remaining (concat before [incorrect-x] after)]
                                                        (recur new-remaining rules new-updates))
                                                      (if (seq rest)
                                                        (recur rest (filterv (fn [[x _]] (not= x to-check)) rules) (conj new-updates to-check))
                                                        (conj new-updates to-check)))))]


                              updated-pages))
                          incorrect-updates)]
    (->>
      updated-updates
      (mapv #(nth % (quot (count %) 2)))
      (apply +))))
