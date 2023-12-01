(ns year2022.day7.puzzles
  (:require
    [clojure.string :as string]
    [clojure.edn :as edn]))

(defn read-input [] (string/split (slurp "src/year2022/day7/input.edn") #"\n"))

(defn get-sizes [children] (mapv edn/read-string (filterv #(re-seq #"\d+" %) children)))

(defn build-tree [input]
  (loop [tree {}
         path []
         commands input]
    (if (empty? commands)
      tree
      (let [[cmd & rest] commands
            [_ _ dir] (string/split cmd #" ")]
        (cond
          (= cmd "$ cd /") (recur tree ["/"] rest)
          (= cmd "$ ls") (recur
                           (assoc tree path (get-sizes (take-while #(not= \$ (first %)) rest)))
                           path
                           (drop-while #(not= \$ (first %)) rest))
          (= cmd "$ cd ..") (recur tree (vec (drop-last path)) rest)
          :else (recur tree (conj path dir) rest))))))

(defn directory-size [directory]
  (let [paths-with-directory (filterv (fn [[p _]] (contains? (into #{} p) directory)) (build-tree (read-input)))]
    (reduce + (flatten (mapv second paths-with-directory)))))

(defn set-total-sizes [tree]
  (reduce
    (fn [new-tree [path _]]
      (let [directory (first (take-last 1 path))]
        (assoc new-tree path (directory-size directory))))
    {}
    tree))

(defn total-size [directory]
  (let [subdirectory? (fn [directory [path _]] (= directory (take (count directory) path)))
        directories (filter (partial subdirectory? directory) (build-tree (read-input)))]
    (reduce + (mapcat second directories))))

(defn part-1 []
  (->>
    (read-input)
    (build-tree)
    (set-total-sizes)
    (mapv first)
    (mapv total-size)
    (filterv #(<= % 100000))
    (reduce +)))

(defn part-2 []
  (->>
    (read-input)
    (build-tree)
    (mapv first)
    (mapv total-size)
    (filter #(>= % (- 30000000 (- 70000000 (total-size ["/"])))))
    (apply min)))
