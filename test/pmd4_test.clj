(ns pmd4-test
  (:require [clojure.test :refer [deftest]]
            [test-validation :refer [test-validation]]
            [rdf-validator.endpoint :as ep]
            [rdf-validator.core :as rv])
  (:import [java.net URI]))

(deftest dataset-max-one-contents
  (test-validation
    "pmd4/src/swirrl/validations/pmd4/SELECT_DatasetMaxOneContents.sparql"
    "test/pmd4/swirrl/validations/pmd4/SELECT_DatasetMaxOneContents.ttl"
    #{{:count 2N :dataset (URI. "http://test/contents/2#fail")}}))

(deftest dataset-exactly-one-title
  (test-validation
    "pmd4/src/swirrl/validations/pmd4/SELECT_DatasetExactlyOneTitle.sparql"
    "test/pmd4/swirrl/validations/pmd4/SELECT_DatasetExactlyOneTitle.ttl"
    #{{:count 0N :dataset (URI. "http://test/title/0#fail")}
      {:count 2N :dataset (URI. "http://test/title/2#fail")}}))

