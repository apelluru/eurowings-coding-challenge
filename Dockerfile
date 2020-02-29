# Build
FROM maven:3.5.2-jdk-8-alpine AS MAVEN_BUILD
MAINTAINER Ashok Pelluru <ashokmca07@gmail.com>

COPY pom.xml /build/
COPY src /build/src/
WORKDIR /build/
RUN mvn package


#Create Image
FROM openjdk:8-jdk-alpine

#change working dir
WORKDIR /opt/app
COPY --from=MAVEN_BUILD /build/target/newsletter-0.0.1-SNAPSHOT.jar .

# Make port 8080 available to the world outside this container
EXPOSE 8080

ENTRYPOINT ["java","-jar","/opt/app/newsletter-0.0.1-SNAPSHOT.jar"]