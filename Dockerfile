FROM openjdk:21-slim

LABEL mentainer="javaguides.net@gmail.com"

WORKDIR /app

COPY target/api-cliente-0.0.1-SNAPSHOT.jar /app/api-cuenta-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "api-cuenta-0.0.1-SNAPSHOT.jar"]