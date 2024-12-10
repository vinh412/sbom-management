package com.vinhdd.sbom.api.dto.queryout;

import com.vinhdd.sbom.api.util.helper.Col;
import lombok.Data;

import java.util.List;

@Data
public class ComponentDtoQueryOut {
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
    @Col(value = "license_id", jsonString = true)
    private List<String> licenseId;
}
