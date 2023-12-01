(ns utils
  (:require [clojure.string :as string]))

(defn read-rows [path] (flatten (map #(string/split-lines %) (string/split (slurp path) #"\n\n"))))
