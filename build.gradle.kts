import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    application
    java
    id("com.github.johnrengelman.shadow") version "4.0.3"
    kotlin("jvm") version "1.3.10"
}

application {
    mainClassName = "net.nprod.db.ontopadapter.MainKt"
}

var ktor_version = "1.0.1"
var ontop_version = "3.0.0-beta-2"
var version = "0.1-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    compile(kotlin("stdlib-jdk8"))
    compile(group = "io.ktor", name = "ktor-server-core", version = ktor_version)
    compile(group = "io.ktor", name = "ktor-server-netty", version = ktor_version)
    compile(group = "io.ktor", name = "ktor-features", version = ktor_version)
    compile("org.eclipse.rdf4j:rdf4j-queryresultio-sparqljson:2.2.2")
    compile(group = "it.unibz.inf.ontop", name = "ontop-system-sql-owlapi", version = ontop_version)
    compile(group = "it.unibz.inf.ontop", name = "ontop-rdf4j", version = ontop_version)
    compile(group = "it.unibz.inf.ontop", name = "ontop-owlapi", version = ontop_version)
    runtime(group = "net.sourceforge.owlapi", name = "owlapi-distribution", version = "5.1.8")
    // You may want to use another JDBC provider or none at all and give them in the java command line
    runtime("org.postgresql:postgresql:42.2.5")
    //testCompile("junit", "junit", "4.12")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}

val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}

tasks.withType<ShadowJar> {
    baseName = "ontopadapter"
    manifest {
        attributes["Main-Class"] = "net.nprod.db.ontopadapter.MainKt"
    }

    classifier = ""
    version = version

    mergeServiceFiles()
}