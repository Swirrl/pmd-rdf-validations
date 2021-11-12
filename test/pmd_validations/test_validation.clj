(ns pmd-validations.test-validation
  (:require [clojure.test :refer [is]]
            [clojure.java.io :as io]
            [rdf-validator.endpoint :as ep]
            [rdf-validator.test-cases :as tc]
            [rdf-validator.core :as rv]))

(defn run-validation
  "Run the sparql query in query-file against the data in ttl-data-file."
  [query-file ttl-data-file]
  (let [test-case {:source query-file}
        repository (ep/parse-repository ttl-data-file)
        endpoint (ep/create-endpoint repository [])]
    (rv/run-test-case test-case {} endpoint)))

(defn test-validation
  "Check the (unordered) errors from run-validation are the same as expected."
  [query-file ttl-data-file expected]
  (let [result (run-validation query-file ttl-data-file)]
    (is (= expected (set (:errors result))))))

(defn run-validation-suite
  "Run the validation in suite-source-file (e.g. rdf-validator-suite.edn)
  against the data in ttl-data-file.
  Returns [summary test-results]."
  [suite-source-file ttl-data-file]
  (let [suite-source (io/file suite-source-file)
        repository (ep/parse-repository ttl-data-file)
        endpoint (ep/create-endpoint repository [])
        suites (tc/resolve-test-suites [suite-source])
        test-cases (tc/suite-tests suites [])
        test-results (rv/run-test-cases test-cases {} endpoint)
        summary (reduce (fn [summary {:keys [result]}]
                          (update summary result inc))
                        {:failed 0 :passed 0 :errored 0 :ignored 0}
                        test-results)]
    [summary test-results]))

