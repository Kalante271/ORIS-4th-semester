# Dockerfile
#docker build -t weather-publisher .
#docker run --rm weather-publisher

FROM eclipse-temurin:23-jdk

WORKDIR /app

COPY target/Lab_08-1.0-SNAPSHOT.jar app.jar

CMD ["java", "-jar", "app.jar"]