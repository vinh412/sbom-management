package com.vinhdd.sbom.api.dto.queryout;

import com.vinhdd.sbom.api.util.helper.Col;
import lombok.Data;

@Data
public class ComponentDtoQueryOut {
    private Long id;
    private String name;
    @Col("group_name")
    private String groupName;
    private String purl;
    private String publisher;
    private String description;
    private String version;
    public boolean equalsExcludeVersion(ComponentDtoQueryOut component) {
        return this.purl.substring(0, this.purl.lastIndexOf("@")).equals(component.getPurl().substring(0, component.getPurl().lastIndexOf("@")));
    }
    public boolean equals(ComponentDtoQueryOut component) {
        return this.purl.equals(component.getPurl());
    }
}
