package com.vinhdd.sbom.api.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "sbom")
@Data
public class Sbom {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String bomFormat;
    private int bomVersion;
    private String specVersion;
    private String serialNumber;
}
