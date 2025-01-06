package com.vinhdd.sbom.api.dto.queryout;

import com.fasterxml.jackson.databind.JsonNode;
import com.vinhdd.sbom.api.util.helper.Col;
import lombok.Data;

import java.util.List;

@Data
public class VulnerabilitiesWithComponentsDtoQueryOut {
    private Long id;
    @Col("vul_id")
    private String vulId;
    @Col("display_name")
    private String displayName;
    private String title;
    private String description;
    @Col("cvss_score")
    private Double cvssScore;
    @Col("cvss_vector")
    private String cvssVector;
    private String cwe;
    private String cve;
    private String reference;
    @Col(value = "external_references", jsonString = true)
    private List<String> externalReferences;
    @Col(value = "components", jsonString = true)
    private List<JsonNode> components;
}
