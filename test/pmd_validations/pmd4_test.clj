(ns pmd-validations.pmd4-test
  (:require [clojure.test :refer [deftest is]]
            [pmd-validations.test-validation
             :refer [test-validation run-validation-suite]]
            [rdf-validator.endpoint :as ep]
            [rdf-validator.core :as rv])
  (:import [java.net URI]))

(deftest dataset-max-one-contents
  (test-validation
    "pmd4/src/swirrl/validations/pmd4/SELECT_DatasetMaxOneContents.sparql"
    "test/pmd4/swirrl/validations/pmd4/SELECT_DatasetMaxOneContents.ttl"
    #{{:count 2N :dataset (URI. "http://test/dataset/2#fail")}}))

(deftest dataset-exactly-one-title
  (test-validation
    "pmd4/src/swirrl/validations/pmd4/SELECT_DatasetExactlyOneTitle.sparql"
    "test/pmd4/swirrl/validations/pmd4/SELECT_DatasetExactlyOneTitle.ttl"
    #{{:count 0N :dataset (URI. "http://test/dataset/0#fail")}
      {:count 2N :dataset (URI. "http://test/dataset/2#fail")}}))

(deftest dataset-min-one-graph
  (test-validation
    "pmd4/src/swirrl/validations/pmd4/SELECT_DatasetMinOneGraph.sparql"
    "test/pmd4/swirrl/validations/pmd4/SELECT_DatasetMinOneGraph.ttl"
    #{{:dataset (URI. "http://test/dataset/0#fail")}}))

; Run the whole suite against removed validations.
(deftest removed-validations
  (let [[summary results]
        (run-validation-suite "pmd4/src/rdf-validator-suite.edn"
                              "test/pmd4/swirrl/validations/pmd4/removed.ttl")]
    (is (= [] (vec (filter #(not= :passed (:result %)) results))))
    (is (= {:passed 33 :failed 0 :errored 0 :ignored 0} summary))))

