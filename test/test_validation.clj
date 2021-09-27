(ns test-validation
  (:require [clojure.test :refer [is]]
            [rdf-validator.endpoint :as ep]
            [rdf-validator.core :as rv]))

(defn test-validation
  [query-file ttl-data-file expected]
  (let [test-case {:source query-file}
        repository (ep/parse-repository ttl-data-file)
        endpoint (ep/create-endpoint repository [])]
    (is (= expected
           (set (:errors (rv/run-test-case test-case {} endpoint)))))))


