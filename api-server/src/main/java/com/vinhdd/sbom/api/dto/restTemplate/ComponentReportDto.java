package com.vinhdd.sbom.api.dto.restTemplate;

import lombok.Data;

import java.util.List;

@Data
public class ComponentReportDto {
    private String coordinates;
    private String description;
    private String reference;
    private List<VulnerabilityDto> vulnerabilities;
}
