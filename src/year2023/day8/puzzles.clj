(ns year2023.day8.puzzles
  (:require
    [clojure.string :as string]
    [utils :as utils]))

(def input (slurp (utils/input-file-path 2023 8)))

(defn map-input []
  (let [[instructions network] (string/split input #"\n\n")
        instructions (string/split instructions #"")
        network (utils/read-rows network)
        network (into {}
                  (mapv
                    (fn [node]
                      (let [[element next-elements] (string/split node #" = ")
                            next-elements (re-seq #"[A-Z]{3}" next-elements)]
                        {element next-elements}))
                    network))]
    [instructions network]))

(defn end-reached? [passed-elements part]
  (if (= part 1) (= (last passed-elements) "ZZZ") (string/ends-with? (last passed-elements) "Z")))

(defn navigate-network
  "Starting at `start-element`, follows the instructions to the next element
  until it reaches the end. Returns a path ofthe start of all nodes it went
  past. When it went through all the instructions, it starts back at i = 0,
  repeating the instructions again."
  [start-element instructions network part]
  (loop [passed-elements [start-element]
         i 0]
    (if (end-reached? passed-elements part)
      passed-elements
      (let [i (if (= i (count instructions)) 0 i)
            instruction (get instructions i)
            element-to-check (get network (last passed-elements))
            next-element (if (= instruction "L") (first element-to-check) (last element-to-check))]
        (recur
          (conj passed-elements next-element)
          (inc i))))))

(defn part-1 []
  (let [[instructions network] (map-input)
        path (navigate-network "AAA" instructions network 1)]
    (dec (count path))))

(defn part-2 []
  (let [[instructions network] (map-input)
        start-elements (keep (fn [[element _]] (when (string/ends-with? element "A") element)) network)
        paths (mapv #(navigate-network % instructions network 2) start-elements)]
    (->>
      paths
      (mapv #(dec (count %)))
      (apply utils/lcm-seq))))
