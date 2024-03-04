FROM openjdk:21
COPY target/*.jar app.jar
COPY src/main/resources/schema.sql /docker-entrypoint-initdb.d/
ENTRYPOINT ["java","-jar","/app.jar"]