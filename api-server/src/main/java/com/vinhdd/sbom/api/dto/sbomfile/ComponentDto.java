package com.vinhdd.sbom.api.dto.sbomfile;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.vinhdd.sbom.api.util.deserializer.RawJsonDeserializer;
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
    @JsonDeserialize(using = RawJsonDeserializer.class)
    private String hashes;
    private List<LicenseWrapper> licenses = new ArrayList<>();
    private String purl;
    @JsonDeserialize(using = RawJsonDeserializer.class)
    private String externalReferences;
    private String type;
    @JsonAlias(value = {"bom-ref"})
    private String bomRef;
    @JsonDeserialize(using = RawJsonDeserializer.class)
    private String evidence;
    @JsonDeserialize(using = RawJsonDeserializer.class)
    private String properties;
}

@Data
class ExternalReference {
    private String type;
    private String url;
}

