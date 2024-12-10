package com.vinhdd.sbom.api.dto.out;

import com.vinhdd.sbom.api.model.Component;
import com.vinhdd.sbom.api.model.License;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class ComponentDtoOut {
    private Long id;
    private String publisher;
    private String groupName;
    private String name;
    private String version;
    private String description;
    private String scope;
    private Set<License> licenses;
    private String purl;
    private String externalReferences;
    private String type;
    private String bomRef;
    private RecursiveDependencyDto dependencies;

    public static ComponentDtoOut from(Component component) {
        ComponentDtoOut componentDtoOut = new ComponentDtoOut();
        componentDtoOut.setId(component.getId());
        componentDtoOut.setPublisher(component.getPublisher());
        componentDtoOut.setGroupName(component.getGroupName());
        componentDtoOut.setName(component.getName());
        componentDtoOut.setVersion(component.getVersion());
        componentDtoOut.setDescription(component.getDescription());
        componentDtoOut.setScope(component.getScope());
        componentDtoOut.setLicenses(component.getLicenses());
        componentDtoOut.setPurl(component.getPurl());
        componentDtoOut.setExternalReferences(component.getExternalReferences());
        componentDtoOut.setType(component.getType());
        componentDtoOut.setBomRef(component.getBomRef());
        return componentDtoOut;
    }
}


