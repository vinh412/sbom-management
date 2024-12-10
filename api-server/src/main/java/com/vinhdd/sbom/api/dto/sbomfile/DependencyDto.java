package com.vinhdd.sbom.api.dto.sbomfile;

import lombok.Data;

import java.util.Set;

@Data
public class DependencyDto {
    private String ref;
    private Set<String> dependsOn;
}
