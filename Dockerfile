FROM openjdk:8-jdk-alpine
VOLUME /main-app
ADD build/libs/spring-postgresql-compose-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 9215
ENTRYPOINT ["java", "-jar","/app.jar"]
