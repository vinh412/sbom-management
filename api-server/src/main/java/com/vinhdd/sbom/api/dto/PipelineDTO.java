package com.vinhdd.sbom.api.dto;

import com.vinhdd.sbom.api.model.Pipeline;
import com.vinhdd.sbom.api.util.helper.Col;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PipelineDTO {
    private String id;
    private String name;
    private String description;
//    private String branchUrl;
    private String branch;
    @Col(value = "created_at", isTimestamp = true)
    private String createdAt;
    @Col(value = "updated_at", isTimestamp = true)
    private String updatedAt;

    public static PipelineDTO from(Pipeline entity) {
        return PipelineDTO.builder()
            .id(entity.getId())
            .name(entity.getName())
            .description(entity.getDescription())
//            .branchUrl(entity.getProject().getRepository().split("\\.")[0] + "/tree/" + entity.getBranch())
            .branch(entity.getBranch())
            .createdAt(entity.getCreatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")))
            .updatedAt(entity.getUpdatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")))
            .build();
    }
}
