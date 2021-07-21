# BUILD
FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

# RUN
FROM openjdk:11-jre-stretch
COPY target/POTTER-web-0.0.1-SNAPSHOT.jar POTTER-web-0.0.1-SNAPSHOT.jar
EXPOSE 8080
CMD java -jar POTTER-web-0.0.1-SNAPSHOT.jar
