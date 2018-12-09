package net.nprod.db.ontopadapter

import it.unibz.inf.ontop.injection.OntopSQLOWLAPIConfiguration
import it.unibz.inf.ontop.rdf4j.repository.OntopRepository

import java.io.IOException

import org.eclipse.rdf4j.query.QueryLanguage
import org.eclipse.rdf4j.query.resultio.sparqljson.SPARQLResultsJSONWriter

import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.engine.commandLineEnvironment
import java.io.ByteArrayOutputStream


private val owlFile = "/config/db.owl"
private val obdaFile = "/config/db.obda"
private val propertyFile = "config/db.properties"

val configuration = OntopSQLOWLAPIConfiguration.defaultBuilder()
    .ontologyFile(owlFile)
    .nativeOntopMappingFile(obdaFile)
    .propertyFile(propertyFile)
    //.enableTestMode()
    .build()

val repo = OntopRepository.defaultRepository(configuration)




@Throws(IOException::class)
fun queryOntop(repo: OntopRepository, sparqlQuery: String): String {
    val st = ByteArrayOutputStream()
    val sb = StringBuilder()
    repo.connection.use { conn ->
        conn.prepareTupleQuery(QueryLanguage.SPARQL, sparqlQuery)
            .evaluate(SPARQLResultsJSONWriter(st))
    }

    sb.append(st)
    return sb.toString()
}

fun main(args: Array<String>) {
    // The ontop repository is initialized at this stage
    // We need to find a way to get that to restart in case of issues
    repo.initialize()
    // The command line arguments are given to netty (allows for the mandatory custom configuration)
    embeddedServer(Netty, commandLineEnvironment(args)).start(wait = true)
    repo.shutDown()
}

data class Entry(val message: String)