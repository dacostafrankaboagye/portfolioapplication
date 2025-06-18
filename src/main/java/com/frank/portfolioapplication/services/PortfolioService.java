package com.frank.portfolioapplication.services;

import com.frank.portfolioapplication.models.Project;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class PortfolioService {
    private List<Project> projects = Arrays.asList(
            Project.builder()
                    .id(1L)
                    .name("AI Chatbot")
                    .description("Intelligent chatbot using NLP and machine learning")
                    .technologies("Python, TensorFlow, Flask")
                    .githubUrl("https://github.com/user/ai-chatbot")
                    .demoUrl("https://demo.ai-chatbot.com")
                    .build(),
            Project.builder()
                    .id(2L)
                    .name("Computer Vision App")
                    .description("Object detection and image classification system")
                    .technologies("Python, OpenCV, PyTorch")
                    .githubUrl("https://github.com/user/cv-app")
                    .demoUrl("https://demo.cv-app.com")
                    .build(),
            Project.builder()
                    .id(3L)
                    .name("ML Prediction API")
                    .description("RESTful API for machine learning predictions")
                    .technologies("Java, Spring Boot, Scikit-learn")
                    .githubUrl("https://github.com/user/ml-api")
                    .demoUrl("https://api.ml-predictions.com")
                    .build()
    );

    // get all Projects
    public List<Project> getAllProjects(){
        return projects;
    }

    // get project by id
    public Project getProjectById(Long id){
        return projects
                .stream()
                .filter(project -> project.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
