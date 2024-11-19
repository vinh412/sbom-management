package com.vinhdd.sbom.api.dto.out;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ProjectDTO {
    private String id;
    private String name;
    private List<PipelineDTO> pipelines;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
