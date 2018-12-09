package net.nprod.db.ontopadapter

import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.engine.commandLineEnvironment


val owlFile = "/config/db.owl"
val obdaFile = "/config/db.obda"
val propertyFile = "/config/db.properties"


fun main(args: Array<String>) {
    // The ontop repository is initialized at this stage
    // We need to find a way to get that to restart in case of issues
    repo.initialize()
    // The command line arguments are given to netty (allows for the mandatory custom configuration)
    embeddedServer(Netty, commandLineEnvironment(args)).start(wait = true)
    repo.shutDown()
}
