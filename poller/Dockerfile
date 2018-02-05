FROM openjdk:8-jdk-alpine

ARG JAR_FILE
ADD ${JAR_FILE} poller.jar
ENTRYPOINT ["java", "-jar", "poller.jar", "--server.port=80", "--supplier.url=http://supplier-red-mockers-m2.qa.ffdc.tradingbell.men", "--mocker.url=http://mocker-red-mockers-m2.qa.ffdc.tradingbell.men" ]
