package com.vinhdd.sbom.api.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "licenses")
@Data
public class License {
    @Id
    @GeneratedValue
    private Long id;
    @Column(length = 36)
    private String uuid;
    private String licenseId;
    private String name;
    @Column(columnDefinition = "TEXT")
    private String text;
    @Column(columnDefinition = "TEXT")
    private String template;
    @Column(columnDefinition = "TEXT")
    private String header;
    @Column(columnDefinition = "TEXT")
    private String comment;

    private byte[] seeAlso;
    private boolean isOsiApproved;
    private boolean isDeprecated;
    private boolean isCustomLicense;
    private boolean fsfLibre;
}
