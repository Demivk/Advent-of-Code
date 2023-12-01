(ns year2023.calendar
  (:require
    [clojure.tools.namespace.repl :refer [refresh]]
    [year2023.day1.day1 :as day-1]))

(def go refresh)

(comment
  go
  (day-1/part-1)
  (day-1/part-2))
