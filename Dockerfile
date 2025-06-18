# Use a multi-stage build for a smaller final image

# Stage 1: Build the application
FROM eclipse-temurin:21-alpine AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven wrapper files and pom.xml first to leverage Docker cache
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Download dependencies (only if pom.xml changes, very efficient caching)
RUN ./mvnw dependency:go-offline -B

# Copy the rest of the application code
COPY src src

# Build the Spring Boot application, creating a JAR file
RUN ./mvnw package -DskipTests

# Stage 2: Create the final production image
FROM eclipse-temurin:21-alpine

# Set the working directory
WORKDIR /app

# Copy the built JAR file from the build stage
# The JAR name typically follows the artifactId-version pattern from pom.xml
ARG JAR_FILE=target/portfolioapplication-0.0.1-SNAPSHOT.jar
COPY --from=build /app/${JAR_FILE} app.jar

# Expose the port your Spring Boot app listens on (now correctly only declares the port)
EXPOSE 8081

# Run the application when the container starts
ENTRYPOINT ["java", "-jar", "app.jar"]

# Add labels for better image management (optional)
LABEL org.opencontainers.image.authors="Frank Aboagye"
LABEL org.opencontainers.image.description="Dev Portfolio Spring Boot Application"
LABEL org.opencontainers.image.source="https://github.com/dacostafrankaboagye/portfolioapplication"
