FROM openjdk:17-jdk-slim

# Update package list and install curl in one step
RUN apt-get update && apt-get install -y curl

LABEL org.opencontainers.image.authors="eroom"

COPY target/gatewayserver.jar gatewayserver.jar

## Verify curl installation
#RUN curl --version

ENTRYPOINT ["java", "-jar", "gatewayserver.jar"]
