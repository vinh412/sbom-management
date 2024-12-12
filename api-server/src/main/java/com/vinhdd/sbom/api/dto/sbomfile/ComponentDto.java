package com.vinhdd.sbom.api.dto.sbomfile;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.databind.JsonNode;
import com.vinhdd.sbom.api.model.Component;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ComponentDto {
    private String publisher;
    private String group;
    private String name;
    private String version;
    private String description;
    private String scope;
    private JsonNode hashes;
    private List<LicenseWrapper> licenses = new ArrayList<>();
    private String purl;
    private JsonNode externalReferences;
    private String type;
    @JsonAlias(value = {"bom-ref"})
    private String bomRef;
    private JsonNode evidence;
    private JsonNode properties;

    public Component toEntity(){
        Component component = new Component();
        component.setPublisher(this.publisher);
        component.setGroupName(this.group);
        component.setName(this.name);
        component.setVersion(this.version);
        component.setDescription(this.description);
        component.setScope(this.scope);
        component.setHashes(this.hashes);
        component.setPurl(this.purl.substring(0, this.purl.indexOf("?")));
        component.setExternalReferences(this.externalReferences);
        component.setType(this.type);
        component.setBomRef(this.bomRef);
        component.setEvidence(this.evidence);
        component.setProperties(this.properties);
        return component;
    }
}

