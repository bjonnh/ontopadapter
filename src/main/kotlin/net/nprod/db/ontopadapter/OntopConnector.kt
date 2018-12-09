package net.nprod.db.ontopadapter

import it.unibz.inf.ontop.injection.OntopSQLOWLAPIConfiguration
import it.unibz.inf.ontop.rdf4j.repository.OntopRepository
import org.eclipse.rdf4j.query.QueryLanguage
import org.eclipse.rdf4j.query.resultio.sparqljson.SPARQLResultsJSONWriter
import java.io.ByteArrayOutputStream
import java.io.IOException


val configuration: OntopSQLOWLAPIConfiguration = OntopSQLOWLAPIConfiguration.defaultBuilder()
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
