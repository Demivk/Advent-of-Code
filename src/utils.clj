(ns utils
  (:require
    [clojure.string :as string]))

(defn input-file-path [year day] (str "src/year" year "/day" day "/input.edn"))

(defn read-rows [input] (flatten (mapv #(string/split-lines %) (string/split input #"\n"))))

(defn read-grid [input] (mapv #(string/split % #"") (read-rows input)))

(defn split-whitespace [s] (string/split s #" "))

(defn num? [s] (some? (first (re-matches #"\d+(\.\d+)?" (str s)))))

(defn parse-int [s] (Integer/parseInt s))

(defn parse-big-int [s] (BigInteger. s))

(defn get-ints [s] (mapv parse-int (re-seq #"\d+" s)))

(defn get-big-ints [s] (mapv parse-big-int (re-seq #"\d+" s)))
