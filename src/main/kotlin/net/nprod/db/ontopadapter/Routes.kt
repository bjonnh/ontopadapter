package net.nprod.db.ontopadapter

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.request.receiveText
import io.ktor.response.header
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.post
import io.ktor.routing.routing

fun Application.mainModule() {
    // This module is called my netty from its configuration file
    routing {
        post("/sparql") {
            val sparqlQuery = call.receiveText()


            try {
                val out = queryOntop(repo, sparqlQuery)
                call.response.header("Access-Control-Allow-Origin", "*")
                call.respondText(out, ContentType.Text.Plain)
            } catch (e: Exception) {
                e.printStackTrace()
                call.respond(HttpStatusCode.UnprocessableEntity, "Impossible to process that query.")
            }
        }
    }
}