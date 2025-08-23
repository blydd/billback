FROM openjdk:21-jdk-slim
WORKDIR /app
COPY target/*.jar billback.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "billback.jar"]
