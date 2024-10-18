FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/lab-app-1.0-jar-with-dependencies.jar /app/postgres-checker.jar

ENTRYPOINT ["java", "-jar", "postgres-checker.jar"]
