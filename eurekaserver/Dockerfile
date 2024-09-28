FROM openjdk:17-jdk-slim

## Update package list and install curl in one step
RUN apt-get update && apt-get install -y curl

LABEL org.opencontainers.image.authors="eroom"

COPY target/eurekaserver.jar eurekaserver.jar

ENTRYPOINT ["java", "-jar", "eurekaserver.jar"]