FROM maven:3.6.0-jdk-11-slim AS build

COPY pom.xml /build/

COPY src /build/src

WORKDIR /build/

#RUN mvn package
RUN ["mvn", "package", "-Dmaven.test.skip=true"]

FROM openjdk:11.0.6-jre-slim

WORKDIR /app

COPY --from=build /build/target/videoteka-0.0.1-SNAPSHOT.jar /app/

ENTRYPOINT ["java", "-jar", "videoteka-0.0.1-SNAPSHOT.jar"]

EXPOSE 8200