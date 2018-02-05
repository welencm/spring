FROM openjdk:8-jdk-alpine

ARG JAR_FILE
ADD ${JAR_FILE} banker.jar
ENTRYPOINT ["java", "-jar", "banker.jar", "--server.port=80"]
