package com.frank.portfolioapplication.controllers;


import com.frank.portfolioapplication.models.Project;
import com.frank.portfolioapplication.services.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioService portfolioService;

    @GetMapping("/")
    public String home(Model model) {
        List<Project> projects = portfolioService.getAllProjects();
        model.addAttribute("projects", projects);
        model.addAttribute("title", "AI Developer Portfolio");
        return "index";
    }

    @GetMapping("/project/{id}")
    public String projectDetail(@PathVariable Long id, Model model) {
        Project project = portfolioService.getProjectById(id);
        model.addAttribute("project", project);
        return "project-detail";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", "About - AI Developer");
        return "about";
    }
}
