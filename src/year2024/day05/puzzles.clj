(ns year2024.day05.puzzles
  (:require
    [clojure.string :as string]))

(def input (slurp (utils/input-file-path 2024 5)))

(defn input->rules-and-production-seq [input]
  (let [[rules production-seq] (utils/split-double-new-lines input)
        rules (->>
                (utils/split-new-line rules)
                (mapv #(mapv utils/parse-int (string/split % #"\|"))))
        production-seq (->>
                         (utils/split-new-line production-seq)
                         (mapv #(mapv utils/parse-int (string/split % #","))))]
    [rules production-seq]))

(defn remove-checked-from-rules [to-check rules] (filterv (fn [[x _]] (not= x to-check)) rules))

(defn get-middle [coll] (nth coll (quot (count coll) 2)))

(defn part-1 []
  (let [[rules production-seq] (input->rules-and-production-seq input)
        correct-pages (keep
                        (fn [pages]
                          (let [rules-for-update (filterv #(every? (into #{} pages) %) rules)
                                right-order? (loop [remaining pages
                                                    rules rules-for-update]
                                               (let [[to-check & rest] remaining]
                                                 (cond
                                                   (some (fn [[_ y]] (= y to-check)) rules) false
                                                   (seq rest) (recur rest (remove-checked-from-rules to-check rules))
                                                   :else true)))]
                            (when right-order? pages)))
                        production-seq)]
    (->>
      correct-pages
      (mapv get-middle)
      (apply +))))

(defn part-2 []
  (let [[rules production-seq] (input->rules-and-production-seq input)
        updated-pages (keep
                        (fn [pages]
                          (let [rules-for-update (filterv #(every? (into #{} pages) %) rules)]
                            (loop [remaining pages
                                   rules rules-for-update
                                   new-pages []
                                   incorrect-order? false]
                              (let [[to-check & rest] remaining
                                    incorrect-x (some (fn [[x y]] (when (= y to-check) x)) rules)]
                                (cond
                                  incorrect-x
                                    (let [without-incorrect-x (remove #(= % incorrect-x) remaining)
                                          before (take-while #(not= % to-check) without-incorrect-x)
                                          after (drop-while #(not= % to-check) without-incorrect-x)]
                                      (recur (concat before [incorrect-x] after) rules new-pages true))
                                  (seq rest)
                                    (recur
                                      rest
                                      (remove-checked-from-rules to-check rules)
                                      (conj new-pages to-check)
                                      incorrect-order?)
                                  :else (when incorrect-order? (conj new-pages to-check)))))))
                        production-seq)]
    (->>
      updated-pages
      (mapv get-middle)
      (apply +))))

