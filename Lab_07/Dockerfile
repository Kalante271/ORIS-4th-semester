# Dockerfile

#FROM eclipse-temurin:17
FROM maven:3.6.2-jdk-11

WORKDIR /app

#RUN mkdir -p /app/logs
#COPY target/Lab_07-1.0-SNAPSHOT.jar /app/target/Lab_07-1.0-SNAPSHOT.jar
COPY pom.xml ./
COPY src/ ./src/
RUN mvn package
#CMD ["java", "-version"]
CMD ["java", "-jar", "/app/target/Lab_07-1.0-SNAPSHOT.jar"]