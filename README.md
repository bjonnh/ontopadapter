# Why

I needed a way to use ontologies and SPARQL to query a SQL database through HTTP.

# How

Using [Ontop](http://ontop.inf.unibz.it/), [Kotlin](https://www.kotlinlang.org) and [ktor](https://ktor.io).
It works really with OpenJDK 8 and 11. The example docker uses version 11.

# Principle

This project has an integrated webserver (port configurable) with the following endpoints:

## /sparql

Takes a POST query and a *data* parameter (not a form parameter) that contains a SPARQL query.

Return:
- if successful: a SPARQL json of the bindings
- if __not__ successful: HTTP 1.1/422 (Unprocessable entity) Impossible to process that query.

# Example of usage with the integrated docker-compose file

There is an example of usage in the docker directory. The example
would connect to a postgresql database with two tables: *project* and *user*.

Each project can have another project has its parent and a user as its owner

Any JDBC compatible database is usable. For now postgresql is integrated in the fat-jar. 


The files:
- db.obda: Contains the queries and their mappings to the ontology
- db.owl: The ontology
- db.properties: The database parameters (type of DB, )
- ktor.config: The webserver configuration (port is the main thing you may want to change here)

The Dockerfile will use a build in build/libs/ontopadapter.jar by default.

I will try to keep things up to date, but you way have to check the version in *build.gradle.kts*.

You can use:
```shell
cd docker
cp ../build/lib/ontopadapter.jar .
docker-compose up --build
```

That will build a full testing environment and connect to it using:

```shell
curl -XPOST --data 'select * { ?s ?p ?o.}' http://127.0.0.1:8080
```

And you will get the SPARQL json output for that example query.

# What is left to do

- [ ] Handle errors better
- [ ] Handle the update of parameters
- [ ] Add metrics
- [ ] Find a way to automate tests and CI
- [ ] Allow customization of CORS
- [ ] Restart the Ontop repository (in case of updates or bugs)
