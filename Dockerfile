FROM openjdk:8

WORKDIR /application

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} application.jar

COPY src/main/resources/application-online.properties application.properties

CMD java -jar -Dspring.config.location=application.properties application.jar


