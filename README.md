# Portfolio Application

A Spring Boot web application showcasing a developer portfolio with automated CI/CD pipeline using Jenkins and Docker containerization.

## 🚀 Quick Start

```bash
# Clone the repository
git clone https://github.com/dacostafrankaboagye/portfolioapplication.git
cd portfolioapplication

# Run with Docker (recommended)
docker build -t portfolio-app .
docker run -p 8081:8081 portfolio-app

# Or run locally with Maven
./mvnw spring-boot:run
```

Access the application at `http://localhost:8081`

## 📋 Project Overview

This is a Spring Boot 3.5.0 application built with Java 21, featuring:
- **Web Framework**: Spring Boot with Thymeleaf templating
- **Build Tool**: Maven with wrapper
- **Containerization**: Multi-stage Docker build
- **CI/CD**: Jenkins pipeline automation
- **Architecture**: MVC pattern with controllers, services, and models

## 🐳 Docker Implementation

### Multi-Stage Dockerfile

The application uses an optimized multi-stage Docker build:

```dockerfile
# Stage 1: Build (eclipse-temurin:21-alpine)
- Copies Maven wrapper and dependencies
- Leverages Docker layer caching for dependencies
- Builds the Spring Boot JAR

# Stage 2: Runtime (eclipse-temurin:21-alpine)
- Minimal production image
- Copies only the built JAR
- Exposes port 8081
```

### Key Docker Features

- **Optimized Caching**: Dependencies downloaded only when `pom.xml` changes
- **Small Image Size**: Alpine-based images for minimal footprint
- **Security**: Non-root execution and minimal attack surface
- **Metadata**: Proper OCI labels for image management

### Docker Commands

```bash
# Build image
docker build -t portfolio-app .

# Run container
docker run -d -p 8081:8081 --name portfolio portfolio-app

# View logs
docker logs portfolio

# Stop container
docker stop portfolio
```

## 🔧 Jenkins CI/CD Pipeline

### Pipeline Overview

The Jenkins pipeline (`Jenkinsfile`) implements a complete CI/CD workflow:

1. **Checkout Code** - Pulls latest code from Git repository
2. **Maven Build & Test** - Compiles and tests the application
3. **Docker Build** - Creates containerized image with commit SHA tagging
4. **Docker Push** - Publishes to Docker Hub registry

### Pipeline Configuration

```groovy
Environment Variables:
- DOCKER_REGISTRY: 'dacostafrankaboagye'
- DOCKER_IMAGE_NAME: 'aidev-portfolio'
- DOCKER_CREDENTIALS_ID: 'dockerhub-credentials'
```

### Key Jenkins Features

- **Automated Testing**: Runs Maven tests before deployment
- **Image Tagging**: Uses Git commit SHA for version tracking
- **Credential Management**: Secure Docker Hub authentication
- **Multi-stage Build**: Optimized Docker image creation
- **Error Handling**: Comprehensive post-build notifications

### Jenkins Setup Requirements

1. **Docker Access**: Jenkins agent must have Docker socket access
2. **Credentials**: Configure Docker Hub credentials in Jenkins
3. **Git Integration**: Repository webhook for automatic triggers
4. **Maven**: Maven wrapper handles build dependencies

### Pipeline Stages Detail

```bash
Stage 1: Checkout Code
├── Pulls from main branch
└── Repository: github.com/dacostafrankaboagye/portfolioapplication

Stage 2: Maven Build and Test
├── chmod +x mvnw (permission fix)
├── ./mvnw clean package -DskipTests
└── ./mvnw test

Stage 3: Build Docker Image
├── Extract Git commit SHA
├── Tag: {registry}/{image}:{commit-sha}
└── Tag: {registry}/{image}:latest

Stage 4: Push Docker Image
├── Docker Hub authentication
├── Push versioned image
├── Push latest tag
└── Logout from registry
```

## 🛠️ Local Development

### Prerequisites

- Java 21
- Maven 3.6+ (or use included wrapper)
- Docker (optional)

### Build Commands

```bash
# Clean and compile
./mvnw clean compile

# Run tests
./mvnw test

# Package JAR
./mvnw package

# Run application
./mvnw spring-boot:run

# Skip tests during build
./mvnw package -DskipTests
```

## 📁 Project Structure

```
portfolioapplication/
├── src/main/java/com/frank/portfolioapplication/
│   ├── controllers/          # Web controllers
│   ├── models/              # Data models
│   ├── services/            # Business logic
│   └── PortfolioapplicationApplication.java
├── src/main/resources/
│   ├── templates/           # Thymeleaf templates
│   ├── static/             # Static assets
│   └── application.yaml    # Configuration
├── Dockerfile              # Multi-stage container build
├── Jenkinsfile            # CI/CD pipeline definition
└── pom.xml               # Maven dependencies
```

## 🔧 Configuration

### Application Settings

```yaml
spring:
  application:
    name: portfolioapplication
server:
  port: 8081
```

### Docker Configuration

- **Base Image**: eclipse-temurin:21-alpine
- **Exposed Port**: 8081
- **Working Directory**: /app
- **Entry Point**: java -jar app.jar

## 🚀 Deployment

### Docker Hub Deployment

The Jenkins pipeline automatically:
1. Builds Docker image on code changes
2. Tags with Git commit SHA and 'latest'
3. Pushes to Docker Hub registry
4. Available at: `dacostafrankaboagye/aidev-portfolio`

### Manual Deployment

```bash
# Pull from Docker Hub
docker pull dacostafrankaboagye/aidev-portfolio:latest

# Run in production
docker run -d -p 8081:8081 \
  --name portfolio-prod \
  --restart unless-stopped \
  dacostafrankaboagye/aidev-portfolio:latest
```

## 🧪 Testing

```bash
# Unit tests
./mvnw test

# Integration tests
./mvnw verify

# Test with Docker
docker build -t portfolio-test .
docker run --rm portfolio-test
```

## 📝 Dependencies

- **Spring Boot Starter Web** - Web framework
- **Spring Boot Starter Thymeleaf** - Template engine
- **Lombok** - Code generation
- **Spring Boot Starter Test** - Testing framework

## 🤝 Contributing

1. Fork the repository
2. Create feature branch
3. Commit changes
4. Push to branch
5. Create Pull Request

The Jenkins pipeline will automatically test and build your changes.

## 📄 License

This project is licensed under the MIT License.