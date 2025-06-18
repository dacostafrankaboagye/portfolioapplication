package com.frank.portfolioapplication.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Project {
    private Long id;
    private String name;
    private String description;
    private String technologies;
    private String githubUrl;
    private String demoUrl;
}
