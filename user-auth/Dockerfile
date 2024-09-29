FROM openjdk:17-jdk-slim

# Update package list and install curl in one step
RUN apt-get update && apt-get install -y curl

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} user-auth.jar

ENTRYPOINT ["java", "-jar", "/user-auth.jar"]
