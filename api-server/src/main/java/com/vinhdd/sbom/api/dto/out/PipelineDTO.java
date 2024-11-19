package com.vinhdd.sbom.api.dto.out;

import com.vinhdd.sbom.api.model.Build;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class PipelineDTO {
    private String id;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
