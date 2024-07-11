FROM openjdk:21-slim

LABEL mentainer="Backend Challenge - API Cuenta"

WORKDIR /app

COPY target/api-cuenta-0.0.1-SNAPSHOT.jar /app/api-cuenta-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "api-cuenta-0.0.1-SNAPSHOT.jar"]