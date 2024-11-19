package com.vinhdd.sbom.api.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "licenses")
@Data
public class License {
    @Id
    @GeneratedValue
    private long id;
    private String uuid;
    private String licenseId;
    private String name;
    private String text;
    private String template;
    private String header;
    private String comment;

    private byte[] seeAlso;
    private boolean isOsiApproved;
    private boolean isDeprecated;
    private boolean isCustomLicense;
    private boolean fsfLibre;
}
