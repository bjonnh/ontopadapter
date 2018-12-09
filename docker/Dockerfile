FROM openjdk:11-jre
COPY ../build/lib/ontopadapter-0.1-SNAPSHOT.jar /ontopadapter.jar
RUN mkdir /config
COPY ktor.config /config/ktor.config
COPY db.obda /config/db.obda
COPY db.owl /config/db.owl
COPY db.properties /config/db.properties
CMD java -Xmx128M -jar /ontopadapter.jar -config=/config/ktor.config
