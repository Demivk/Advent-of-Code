(ns utils
  (:require
    [clojure.string :as string]))

(defn input-file-path [year day] (str "src/year" year "/day" day "/input.edn"))

(defn read-rows [input] (flatten (map #(string/split-lines %) (string/split input #"\n"))))

(defn parse-int [s] (Integer/parseInt s))

(defn split-whitespace [s] (string/split s #" "))
