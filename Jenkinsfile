// Define a Jenkins Pipeline
pipeline {
    // Agent specifies where the pipeline will run. 'any' means any available agent.
    // For production, you might specify a specific label, e.g., agent { label 'java-maven-docker' }
    // Now that the Jenkins container has access to Docker socket, 'any' should work.
    agent any

    // Environment variables that can be used throughout the pipeline
    environment {
        // Replace with your Docker Hub username or private registry host/project
        DOCKER_REGISTRY = 'dacostafrankaboagye' // e.g., 'your-dockerhub-username'
        // Or for a private registry: 'myregistry.com/myproject'
        DOCKER_IMAGE_NAME = "aidev-portfolio"
        // Jenkins credential ID for Docker Hub login (configured in Jenkins -> Credentials)
        // This credential should be of type "Username with password"
        DOCKER_CREDENTIALS_ID = 'dockerhub-credentials'
    }

    // Stages define the main steps of your pipeline
    stages {
        // Stage 1: Code Checkout from Git
        stage('Checkout Code') {
            steps {
                echo 'Checking out source code from Git...'
                git branch: 'main', url: 'https://github.com/dacostafrankaboagye/portfolioapplication.git' // Replace with your Git repository URL and branch
            }
        }

        // Stage 2: Build and Test the Spring Boot Application with Maven
        stage('Maven Build and Test') {
            steps {
                echo 'Building and testing Spring Boot application with Maven...'
                // Use the 'dir' step to ensure commands run in the correct directory if needed
                // For a typical Spring Boot project, the pom.xml is at the root.
                script {
                    // IMPORTANT: Grant execute permissions to the Maven Wrapper script
                    sh 'chmod +x mvnw'
                    // Check if Maven is available in the agent's PATH, otherwise use tool { name 'Maven' }
                    sh './mvnw clean package -DskipTests' // Build the JAR, skip tests for now (run separately)
                    sh './mvnw test'                     // Run unit tests
                }
            }
        }

        // Stage 3: Build Docker Image
        stage('Build Docker Image') {
            steps {
                echo 'Building Docker image...'
                script {
                    // Get the current commit SHA to tag the image
                    def gitCommitSha = sh(returnStdout: true, script: 'git rev-parse --short HEAD').trim()
                    def dockerImageTag = "${DOCKER_REGISTRY}/${DOCKER_IMAGE_NAME}:${gitCommitSha}"
                    def latestTag = "${DOCKER_REGISTRY}/${DOCKER_IMAGE_NAME}:latest"

                    // Build the Docker image using the Dockerfile in the current directory
                    // -t for tagging, --build-arg to pass JAR_FILE name (optional, if your pom.xml generates a different name)
                    sh "docker build -t ${dockerImageTag} -t ${latestTag} ."
                }
            }
        }

        // Stage 4: Push Docker Image to Registry
        stage('Push Docker Image') {
            steps {
                echo 'Pushing Docker image to registry...'
                script {
                    // Retrieve the commit SHA again if needed, or pass from previous stage
                    def gitCommitSha = sh(returnStdout: true, script: 'git rev-parse --short HEAD').trim()
                    def dockerImageTag = "${DOCKER_REGISTRY}/${DOCKER_IMAGE_NAME}:${gitCommitSha}"
                    def latestTag = "${DOCKER_REGISTRY}/${DOCKER_IMAGE_NAME}:latest"

                    // Authenticate to Docker registry using defined credentials
                    withCredentials([usernamePassword(credentialsId: DOCKER_CREDENTIALS_ID, passwordVariable: 'DOCKER_PASSWORD', usernameVariable: 'DOCKER_USERNAME')]) {
                        // FIX: Removed the registry URL argument for docker login to default to Docker Hub
                        sh "echo ${DOCKER_PASSWORD} | docker login -u ${DOCKER_USERNAME} --password-stdin"
                        sh "docker push ${dockerImageTag}"
                        sh "docker push ${latestTag}"
                        // FIX: Removed the registry URL argument for docker logout to default to Docker Hub
                        sh "docker logout"
                    }
                }
            }
        }
    }

    // Post-build actions (optional)
    post {
        always {
            echo 'Pipeline finished.'
            // Clean up workspace if desired
            // cleanWs()
        }
        success {
            echo 'Pipeline succeeded!'
            // Add notifications here, e.g., Slack, Email
        }
        failure {
            echo 'Pipeline failed!'
            // Add failure notifications here
        }
    }
}
