package com.vinhdd.sbom.api.dto.queryout;

import com.fasterxml.jackson.databind.JsonNode;
import com.vinhdd.sbom.api.util.helper.Col;
import lombok.Data;

import java.util.List;

@Data
public class DetailComponentDtoQueryOut {
    private Long id;
    @Col("bom_ref")
    private String bomRef;
    private String description;
    @Col("group_name")
    private String groupName;
    private String name;
    private String publisher;
    private String purl;
    private String type;
    private String version;
    @Col("latest_version")
    private String latestVersion;
    @Col("is_latest")
    private Boolean isLatest;
    @Col(value = "licenses", jsonString = true)
    private List<JsonNode> licenses;
    @Col(value = "vulnerabilities", jsonString = true)
    private List<JsonNode> vulnerabilities;
}
