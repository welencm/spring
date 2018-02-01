FROM openjdk:8-jdk-alpine

ARG JAR_FILE
ADD ${JAR_FILE} supplier.jar
ENTRYPOINT ["java", "-jar", "supplier.jar"]
