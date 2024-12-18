package com.vinhdd.sbom.api.dto.queryout;

import lombok.Data;

import java.util.List;

@Data
public class VulnerabilitiesDtoQueryOut {
    private String id;
    private String vulId;
    private String displayName;
    private String title;
    private String description;
    private double cvssScore;
    private String cvssVector;
    private String cwe;
    private String cve;
    private String reference;
    private List<String> externalReferences;
    private List<String> purls;
}
