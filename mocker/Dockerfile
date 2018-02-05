FROM openjdk:8-jdk-alpine

ARG JAR_FILE
ADD ${JAR_FILE} mocker.jar
ENTRYPOINT ["java", "-jar", "mocker.jar", "--server.port=80"]
