package com.vinhdd.sbom.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "sbom")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Sbom {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String bomFormat;
    private int version;
    private String specVersion;
    private String serialNumber;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "component_id", referencedColumnName = "id")
    private Component component;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "sbom_component",
            joinColumns = @JoinColumn(name = "sbom_id"),
            inverseJoinColumns = @JoinColumn(name = "component_id")
    )
    private Set<Component> components = new HashSet<>();

    private LocalDateTime createdAt;
    @CreationTimestamp
    private LocalDateTime uploadedAt;

    public void addComponent(Component component) {
        components.add(component);
        component.getSboms().add(this);
    }
}
