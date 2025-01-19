package com.vinhdd.sbom.api.dto.queryout;

import com.fasterxml.jackson.databind.JsonNode;
import com.vinhdd.sbom.api.util.helper.Col;
import lombok.Data;

import java.util.List;

@Data
public class ComponentQueryOut {
    private Long id;
    @Col("bom_ref")
    private String bomRef;
    private String name;
    private String version;
    private String description;
    @Col("group_name")
    private String groupName;
    private String publisher;
    private String purl;
    private String scope;
    private String type;
    @Col("latest_version")
    private String latestVersion;
    @Col(value = "appear_in", jsonString = true)
    private List<JsonNode> appearIn;
    @Col(value = "external_references", jsonString = true)
    private List<JsonNode> references;
    @Col(value = "hashes", jsonString = true)
    private List<JsonNode> hashes;
}
