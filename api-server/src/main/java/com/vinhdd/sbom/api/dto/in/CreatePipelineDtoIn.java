package com.vinhdd.sbom.api.dto.in;

import lombok.Data;
@Data
public class CreatePipelineDtoIn {
    private String name;
    private String projectId;
}
