# Step 1: Use an official Maven image to build the project
FROM maven:3.9.9-eclipse-temurin-17 AS build

# Set working directory in the container
WORKDIR /app

# Copy the pom.xml and install dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Step 2: Use an OpenJDK image to run the application
FROM openjdk:17-jdk-slim

# Set working directory in the container
WORKDIR /app

# Copy the jar file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Set environment variables if needed (optional)
# ENV JAVA_OPTS="-Xms512m -Xmx1024m"

# Expose the port your Spring Boot application will run on
EXPOSE 8081

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
