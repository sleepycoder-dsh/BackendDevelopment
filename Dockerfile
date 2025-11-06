# Use Eclipse Temurin JDK 17 as base image (replaces openjdk)
FROM eclipse-temurin:17-jdk-jammy

# Set working directory inside the container
WORKDIR /app

# Copy your compiled Spring Boot JAR file into the container
COPY target/*.jar app.jar

# Expose backend port
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
 