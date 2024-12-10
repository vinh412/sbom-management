package com.vinhdd.sbom.api.dto.in;

import lombok.Data;
@Data
public class CreatePipelineDtoIn {
    private String projectId;
    private String name;
    private String description;
}
