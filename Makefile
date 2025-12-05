# Advent of Code Generator
# Usage: make new-day [YEAR=2025] [DAY=6]

YEAR?=$(shell date +%Y)
DAY?=$(shell date +%d)
DAY_PADDED=$(shell printf "%02d" $(DAY))
DAY_DIR=src/year$(YEAR)/day$(DAY_PADDED)
YEAR_DIR=src/year$(YEAR)
CALENDAR_FILE=$(YEAR_DIR)/calendar.clj
PUZZLES_FILE=$(DAY_DIR)/puzzles.clj
INPUT_FILE=$(DAY_DIR)/input.edn

# Template for puzzles.clj
# YEAR and DAY_PADDED will be replaced by Make variables
# DAY (unpadded) is used for input-file-path

define PUZZLES_CLJ
(ns year$(YEAR).day$(DAY_PADDED).puzzles\n  (:require\n    [utils]))\n\n(def input (slurp (utils/input-file-path $(YEAR) $(DAY))))\n\n(defn part-1 [] 0)\n\n(defn part-2 [] 0)\n\n(comment)
endef
export PUZZLES_CLJ

day:
	@if [ -d $(DAY_DIR) ]; then echo "Directory $(DAY_DIR) already exists."; else mkdir -p $(DAY_DIR) && echo "Created directory $(DAY_DIR)."; fi
	@if [ -f $(INPUT_FILE) ]; then echo "File $(INPUT_FILE) already exists."; else touch $(INPUT_FILE) && echo "Created file $(INPUT_FILE)."; fi
	@if [ -f $(PUZZLES_FILE) ]; then echo "File $(PUZZLES_FILE) already exists."; else echo "$$PUZZLES_CLJ" > $(PUZZLES_FILE) && echo "Created file $(PUZZLES_FILE)."; fi
	@# Update calendar.clj with new require and tests
	@if [ -f $(CALENDAR_FILE) ]; then \
		if grep -q "year$(YEAR).day$(DAY_PADDED).puzzles" $(CALENDAR_FILE); then \
			echo "Day $(DAY_PADDED) already exists in $(CALENDAR_FILE)."; \
		else \
			if grep -q "year$(YEAR)\.day[0-9]*\.puzzles" $(CALENDAR_FILE); then \
				sed -i '' 's/\(\[year$(YEAR)\.day[0-9]*\.puzzles :as day-[0-9]*\]\))/\1\n    [year$(YEAR).day$(DAY_PADDED).puzzles :as day-$(DAY_PADDED)])/' $(CALENDAR_FILE); \
			else \
				sed -i '' 's/\(\[clojure\.tools\.namespace\.repl :refer \[refresh\]\]\))/\1\n    [year$(YEAR).day$(DAY_PADDED).puzzles :as day-$(DAY_PADDED)])/' $(CALENDAR_FILE); \
			fi && \
			sed -i '' 's/\(      (println "\\n🎅🏻Total time:")\)/      (println "🎄Day $(DAY_PADDED)")\n      (time (testing "Day $(DAY_PADDED) - part 1" (is (= (day-$(DAY_PADDED)\/part-1) 0))))\n      (time (testing "Day $(DAY_PADDED) - part 2" (is (= (day-$(DAY_PADDED)\/part-2) 0))))\n\n\1/' $(CALENDAR_FILE) && \
			echo "Updated $(CALENDAR_FILE) with day $(DAY_PADDED)."; \
		fi \
	else \
		echo "Warning: $(CALENDAR_FILE) does not exist. Run 'make new-year YEAR=$(YEAR)' first."; \
	fi

# Usage: make new-year [YEAR=2025]

# Template for calendar.clj
# YEAR will be replaced by Make variable

define CALENDAR_CLJ
(ns year$(YEAR).calendar\n  (:require\n    [clojure.test :refer [testing is]]\n    [clojure.tools.namespace.repl :refer [refresh]]))\n\n(def go refresh)\n\n(comment go)\n\n(defn run-timed-tests []\n  (println "✨ $(YEAR) ✨")\n  (time\n    (do\n      (println "\\n🎅🏻Total time:"))))\n\n(comment (run-timed-tests))
endef
export CALENDAR_CLJ

year:
	@if [ -d $(YEAR_DIR) ]; then echo "Directory $(YEAR_DIR) already exists."; else mkdir -p $(YEAR_DIR) && echo "Created directory $(YEAR_DIR)."; fi
	@if [ -f $(CALENDAR_FILE) ]; then echo "File $(CALENDAR_FILE) already exists."; else echo "$$CALENDAR_CLJ" > $(CALENDAR_FILE) && echo "Created file $(CALENDAR_FILE)."; fi
