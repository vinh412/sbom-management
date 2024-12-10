package com.vinhdd.sbom.api.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "builds")
@Data
public class Build {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "sbom_id", referencedColumnName = "id")
    private Sbom sbom;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "pipeline_id")
    private Pipeline pipeline;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public void setPipeline(Pipeline pipeline) {
        this.pipeline = pipeline;
        pipeline.getBuilds().add(this);
    }
}
