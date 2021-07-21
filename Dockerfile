FROM maven:latest as builder
COPY pom.xml /usr/local/pom.xml

COPY POTTER-model/pom.xml /usr/local/POTTER-model/pom.xml
COPY POTTER-model/src /usr/local/POTTER-model/src

COPY POTTER-casas-client/pom.xml /usr/local/POTTER-casas-client/pom.xml
COPY POTTER-casas-client/src /usr/local/POTTER-casas-client/src

COPY POTTER-service/pom.xml /usr/local/POTTER-service/pom.xml
COPY POTTER-service/src /usr/local/POTTER-service/src

COPY POTTER-web/pom.xml /usr/local/POTTER-web/pom.xml
COPY POTTER-web/src /usr/local/POTTER-web/src

WORKDIR /usr/local/

RUN mvn clean install

FROM openjdk:11-jre-stretch
WORKDIR /usr/local/
COPY --from=builder /usr/local/POTTER-web/target/POTTER-web-0.0.1-SNAPSHOT.jar POTTER-web-0.0.1-SNAPSHOT.jar
EXPOSE 8080
CMD java -jar POTTER-web-0.0.1-SNAPSHOT.jar