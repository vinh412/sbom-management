package com.vinhdd.sbom.api.dto.in;

import lombok.Data;

import java.util.Set;

@Data
public class RoleDtoIn {
    private Long id;
    private String name;
    private Set<String> permissionIds;
}
