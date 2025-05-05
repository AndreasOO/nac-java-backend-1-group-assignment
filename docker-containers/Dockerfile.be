FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY ./target/java-backend-1-group-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]