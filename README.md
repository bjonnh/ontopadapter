# Why

I needed a way to use ontologies and SPARQL to query a SQL database through HTTP.

# How

Using [Ontop](http://ontop.inf.unibz.it/), [Kotlin](https://www.kotlinlang.org) and [ktor](https://ktor.io).

# Usage

There is an example of usage in the docker directory. The example
would connect to a postgresql database with two tables: *project_manager_project* and *accounts_user*.

Each project can have another project has its parent and a user as its owner

Any JDBC compatible database is usable. For now postgresql is integrated in the fat-jar. 


The files:
- db.obda: Contains the queries and their mappings to the ontology
- db.owl: The ontology
- db.properties: The database parameters (type of DB, )
- ktor.config: The webserver configuration (port is the main thing you may want to change here)

The Dockerfile will use a build in build/libs/ontopadapter-VERSION.jar by default.

I will try to keep things up to date, but you way have to check the version in *build.gradle.kts*.

# What is left to do

- [ ] Document the use of Ontop (even if it is using standard ontop files, that will be useful)
- [ ] Handle errors better
- [ ] Handle the update of parameters
- [ ] Add metrics

