package com.vinhdd.sbom.api.dto;

import com.vinhdd.sbom.api.model.Project;
import lombok.Builder;
import lombok.Data;

import java.time.format.DateTimeFormatter;

@Data
@Builder
public class ProjectDTO {
    private String id;
    private String name;
    private String description;
    private String repository;
    private String createdAt;
    private String updatedAt;

    public static ProjectDTO from(Project project) {
        return ProjectDTO.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .repository(project.getRepository())
                .createdAt(project.getCreatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")))
                .updatedAt(project.getUpdatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")))
                .build();
    }
}
