# Customising validation suites

You can use `deps.edn` to depend upon validation suites managed as
library dependencies. These dependencies can be either created as
maven artifacts or even easier, point to git commits in git
repositories.

e.g. create a new file named `deps.edn` (location is unimportant) with the following contents:

```clojure
{:deps {;; NOTE each dep here is a validation suite
        swirrl/validations.pmdqb {:git/url "git@github.com:Swirrl/pmd-rdf-validations.git"
                                  :sha "ea669ddb996ba56a7af0dbf5a196c24b08324632"
                                  :deps/manifest :deps
                                  :deps/root "pmd-qb"}
        swirrl/validations.pmd4 {:git/url "git@github.com:Swirrl/pmd-rdf-validations.git"
                                  :sha "ea669ddb996ba56a7af0dbf5a196c24b08324632"
                                  :deps/manifest :deps
                                  :deps/root "pmd4"}
        ;;; Exclude any suites you don't want by commenting them out
        ;;; or removing them.

        ;;swirrl/validations.qb {:git/url "git@github.com:Swirrl/pmd-rdf-validations.git"
        ;;                       :sha "ea669ddb996ba56a7af0dbf5a196c24b08324632"
        ;;                       :deps/manifest :deps
        ;;                       :deps/root "qb"}
        }
 :aliases {:rdf-validator {:extra-deps { swirrl/rdf-validator {:git/url "https://github.com/Swirrl/rdf-validator.git"
                                                               :sha "fd848fabc5718f876f99ee4ee5a3f89ea8529571"}}
                           :main-opts ["-m" "rdf-validator.core"]}
           :local/validations {:classpath-overrides {swirrl/validations.pmdqb "/path/to/local/repo/pmd-rdf-validations/pmd-qb"
                                                     swirrl/validations.pmd4 "/path/to/local/repo/pmd-rdf-validations/pmd4"}}
                     }
 }
```

3. Navigate to the directory containing your newly created `deps.edn` file in the command line and run `clojure -M:rdf-validator --endpoint http://{server}:{port}/your-db-name/query`

## How this works

The above `deps.edn` configuration file declaratively specifies dependencies on two suites of SPARQL query validations.  These two suites happen to located at different paths within the same git repository, though they could easily be in separate repositories.  It additionally then specifies a dependency on the [RDF Validator](https://github.com/Swirrl/rdf-validator) itself under the `:rdf-validator` alias.

Running the command `clojure -M:rdf-validator <args>` will then automatically cause the dependencies to be fetched, cached for future use and put on the JVMs classpath before finally executing the [RDF Validator](https://github.com/Swirrl/rdf-validator) application with the specified suites.

As the [RDF Validator](https://github.com/Swirrl/rdf-validator) supports finding and loading validation suites from the java classpath the dependent suites will then be executed.  The validator defines a simple manifest for discovering and executing suites specified in this manner.

## Trouble shooting

- Make sure that the `:sha` values in your `deps.edn` match the latest commit SHAs for this [pmd-rdf-validations repo](https://github.com/Swirrl/pmd-rdf-validations/commits/master) and the [rdf-validator repo](https://github.com/Swirrl/rdf-validator/commits/master) respectively.
