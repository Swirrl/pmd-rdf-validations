# PMD RDF data validations

A collection of RDF validation queries ready to be
used against RDF data using the [RDF Validator](https://github.com/Swirrl/rdf-validator).

- **pmd-qb** - a set of validations based on the RDF Data Cube IC validations, adjusted slightly to fit the way the RDF Cube has been implemented within Publish My Data
- **pmd4** - as set of validations to check data relating to the catalog structure and other data expectations for PMD4 to function as expected
- **qb** - a verbatim copy of the IC validations from the [RDF Data Cube specification](https://www.w3.org/TR/vocab-data-cube/#h3_wf-rules).

## Pre-requisities

1. Clone this github repo
2. Install clojure cli tools using `brew install clojure/tools/clojure` (see [clojure cli docs](https://clojure.org/guides/getting_started#_clojure_installer_and_cli_tools) for other installations)

## Usage

Change into the repos working directory and run:

```
$ clojure -M:pmd4:validate -e http://my/sparql/endpoint
```

Inspecting this projects `deps.edn` will show that we define the aliases `:pmd4` (for the pmd4 suite), `:pmd-qb` (for the pmd-qb) whilst `:validate` pulls in the swirrl validation tool.

Hence to also run `:pmd-qb` you would simply run `clojure -M:pmd4:pmd-qb:validate -e http://my/sparql/endpoint`

## How this works

The above `deps.edn` configuration file declaratively specifies dependencies on two suites of SPARQL query validations.  These two suites happen to located at different paths within the same git repository, though they could easily be in separate repositories.  It additionally then specifies a dependency on the [RDF Validator](https://github.com/Swirrl/rdf-validator) itself under the `:validate` alias.

Running the command `clojure -M:validate <args>` will then automatically cause the dependencies to be fetched, cached for future use and put on the JVMs classpath before finally executing the [RDF Validator](https://github.com/Swirrl/rdf-validator) application with the specified suites.

As the [RDF Validator](https://github.com/Swirrl/rdf-validator) supports finding and loading validation suites from the java classpath the dependent suites will then be executed.  The validator defines a simple manifest for discovering and executing suites specified in this manner.

## Trouble shooting

- Make sure that the `:sha` values in your `deps.edn` match the latest commit SHAs for this [pmd-rdf-validations repo](https://github.com/Swirrl/pmd-rdf-validations/commits/master) and the [rdf-validator repo](https://github.com/Swirrl/rdf-validator/commits/master) respectively.
