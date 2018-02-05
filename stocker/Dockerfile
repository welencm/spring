FROM openjdk:8-jdk-alpine

ARG JAR_FILE
ADD ${JAR_FILE} stocker.jar
ENTRYPOINT ["java", "-jar", "stocker.jar", "--server.port=80"]
