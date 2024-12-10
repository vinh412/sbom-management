package com.vinhdd.sbom.api.dto.out;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class RecursiveDependencyDto {
    private String ref;
    private Set<RecursiveDependencyDto> dependsOn = new HashSet<>();
}
