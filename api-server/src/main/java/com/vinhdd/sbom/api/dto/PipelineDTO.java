package com.vinhdd.sbom.api.dto;

import com.vinhdd.sbom.api.model.Pipeline;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.parameters.P;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Builder
public class PipelineDTO {
    private String id;
    private String name;
    private String description;
    private String createdAt;
    private String updatedAt;

    public static PipelineDTO from(Pipeline entity) {
        return PipelineDTO.builder()
            .id(entity.getId())
            .name(entity.getName())
            .description(entity.getDescription())
            .createdAt(entity.getCreatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")))
            .updatedAt(entity.getUpdatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")))
            .build();
    }
}