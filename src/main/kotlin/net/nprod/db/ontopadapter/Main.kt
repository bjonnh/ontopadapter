package net.nprod.db.ontopadapter

import io.ktor.application.Application
import it.unibz.inf.ontop.injection.OntopSQLOWLAPIConfiguration
import it.unibz.inf.ontop.rdf4j.repository.OntopRepository

import java.io.IOException

import org.eclipse.rdf4j.query.QueryLanguage
import org.eclipse.rdf4j.query.resultio.sparqljson.SPARQLResultsJSONWriter

import io.ktor.server.engine.embeddedServer
import io.ktor.http.ContentType
import io.ktor.server.netty.Netty
import io.ktor.response.header
import io.ktor.response.respondText
import io.ktor.routing.routing
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receiveText
import io.ktor.response.respond
import io.ktor.routing.post
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


fun Application.mainModule() {
    routing {
        post("/sparql") {
            val sparqlQuery = call.receiveText()


            try {
                var out = queryOntop(repo, sparqlQuery)
                call.response.header("Access-Control-Allow-Origin", "*")
                call.respondText(out, ContentType.Text.Plain)
            } catch (e: Exception) {
                e.printStackTrace()
                call.respond(HttpStatusCode.UnprocessableEntity, "Impossible to process that query.")
            }
        }
    }
}

fun main(args: Array<String>) {
    repo.initialize()

    embeddedServer(Netty, commandLineEnvironment(args)).start(wait = true)
    repo.shutDown()
}

data class Entry(val message: String)