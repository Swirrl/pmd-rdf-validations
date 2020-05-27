# PMD RDF data validations

A collection of RDF validation queries ready to be
used against RDF data using the [RDF Validator](https://github.com/Swirrl/rdf-validator).


## Simple Quick Start:

1. Install clojure cli tools using `brew install clojure/tools/clojure` (see [clojure cli docs](https://clojure.org/guides/getting_started#_clojure_installer_and_cli_tools) for other installations)
2. Create a new file named `deps.edn` (location is unimportant) with the following contents:

```
{:deps {swirrl/validations.pmdqb {:git/url "git@github.com:Swirrl/pmd-rdf-validations.git"
                                  :sha "d59c744116b178d6b0f5436b8d511b467d8a59d4"
                                  :deps/manifest :deps
                                  :deps/root "pmd-qb"}
        swirrl/validations.pmd4 {:git/url "git@github.com:Swirrl/pmd-rdf-validations.git"
                                  :sha "d59c744116b178d6b0f5436b8d511b467d8a59d4"
                                  :deps/manifest :deps
                                  :deps/root "pmd4"}}
 :aliases {:rdf-validator {:extra-deps { swirrl/rdf-validator {:git/url "https://github.com/Swirrl/rdf-validator.git"
                                                          :sha "fd848fabc5718f876f99ee4ee5a3f89ea8529571"
                                                          }}
                      :main-opts ["-m" "rdf-validator.core"]}
           :local/validations {:classpath-overrides {swirrl/validations.pmdqb "/path/to/local/github/projects/pmd-rdf-validations/pmd-qb/src"
           																																										swirrl/validations.pmd4 "/path/to/local/github/projects/pmd-rdf-validations/pmd4/src"}}
                     }
 }
```

3. Navigate to the directory containing your newly created `deps.edn` file in the command line and run `clj -A:rdf-validator --endpoint http://{server}:{port}/your-db-name/query` 

