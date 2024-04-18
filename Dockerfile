FROM eclipse-temurin:21-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
COPY src/main/resources/application.properties /application.properties
EXPOSE 8080
ENTRYPOINT ["java","-jar","-Dspring.config.location=/application.properties","/app.jar"]
