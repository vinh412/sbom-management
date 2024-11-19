package com.vinhdd.sbom.api.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "builds")
@Data
public class Build {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private int sbomId;
    private String pipelineId;
}
