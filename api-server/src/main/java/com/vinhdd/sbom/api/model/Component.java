package com.vinhdd.sbom.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "components")
public class Component {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String publisher;
    private String groupName;
    private String name;
    private String version;

    @Column(columnDefinition = "TEXT")
    private String description;
    private String scope;

    @Column(columnDefinition = "TEXT")
    private String hashes;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "component_license",
            joinColumns = @JoinColumn(name = "component_id"),
            inverseJoinColumns = @JoinColumn(name = "license_id")
    )
    private Set<License> licenses;

    private String purl;

    @Column(columnDefinition = "TEXT")
    private String externalReferences;
    private String type;
    private String bomRef;

    @Column(columnDefinition = "TEXT")
    private String evidence;

    @Column(columnDefinition = "TEXT")
    private String properties;

    @ManyToMany(mappedBy = "components")
    private Set<Sbom> sboms = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "component_dependency",
            joinColumns = @JoinColumn(name = "component_id"),
            inverseJoinColumns = @JoinColumn(name = "dependency_id")
    )
    private Set<Component> dependencies = new HashSet<>();

}
