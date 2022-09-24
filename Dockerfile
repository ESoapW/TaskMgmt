FROM maven:3.8.6-jdk-11-slim as build

COPY src src
COPY pom.xml .
COPY config.yml .

RUN mvn -f ./pom.xml clean package

FROM openjdk:11-jre-slim

COPY --from=build target/TaskMgmt-1.0-SNAPSHOT.jar ./TaskMgmt-1.0-SNAPSHOT.jar
COPY --from=build config.yml ./config.yml

EXPOSE 8080 8081

ENTRYPOINT ["java", "-jar", "TaskMgmt-1.0-SNAPSHOT.jar","server","config.yml"]