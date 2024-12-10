package com.vinhdd.sbom.api.dto;

import com.vinhdd.sbom.api.model.Project;
import lombok.Builder;
import lombok.Data;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
@Builder
public class ProjectWithPipelinesDTO {
    private String id;
    private String name;
    private String description;
    private List<PipelineDTO> pipelines;
    private String createdAt;
    private String updatedAt;

    public static ProjectWithPipelinesDTO from(Project project) {
        return ProjectWithPipelinesDTO.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .pipelines(project.getPipelines().stream().map(PipelineDTO::from).toList())
                .createdAt(project.getCreatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")))
                .updatedAt(project.getUpdatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")))
                .build();
    }
}
