package com.vinhdd.sbom.api.dto.restTemplate;

import lombok.Data;

@Data
public class PackageInfoDto {
    private String name;
    private String version;
    private String license;
    private String description;
}
