package com.vinhdd.sbom.api.dto.in;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class AddMemberDtoIn {
    @NotBlank(message = "Project name is required")
    private String projectName;
    @NotBlank(message = "User ID is required")
    private String userId;
    private Boolean isAdmin = false;
    private List<String> pipelineIds = List.of();
}
