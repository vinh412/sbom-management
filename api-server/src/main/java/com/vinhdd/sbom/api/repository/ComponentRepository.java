package com.vinhdd.sbom.api.repository;

import com.vinhdd.sbom.api.model.Component;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Map;
import java.util.Set;

public interface ComponentRepository extends JpaRepository<Component, Long> {
    Component findByPurl(String purl);
    Component findByBomRef(String bomRef);
    @Query(value = "SELECT d.purl FROM components c JOIN component_dependency cd ON c.id = cd.component_id JOIN components d ON cd.dependency_id = d.id WHERE c.purl = ?1 ", nativeQuery = true)
    Set<String> getAllDependenciesByPurl(String purl);

    @Query(value = "SELECT c.purl AS ref, COALESCE(json_agg(d.purl) FILTER ( WHERE d.purl IS NOT NULL ), '[]') AS dependsOn " +
            "FROM components c " +
            "LEFT JOIN component_dependency cd on c.id = cd.component_id " +
            "LEFT JOIN components d on cd.dependency_id = d.id " +
            "WHERE c.purl = ?1 GROUP BY c.purl", nativeQuery = true)
    Map<String, Object> getDirectDependenciesOfComponent(String purl);

    @Query(value = "SELECT c.*, json_agg(l.license_id) as license_id from components c " +
            "JOIN sbom_component sc on c.id = sc.component_id " +
            "JOIN sbom s on sc.sbom_id = s.id " +
            "JOIN builds b on s.id = b.sbom_id " +
            "JOIN component_license cl ON c.id = cl.component_id " +
            "JOIN licenses l ON cl.license_id = l.id " +
            "WHERE b.id = ?1 GROUP BY c.id", nativeQuery = true)
    Set<Map<String, Object>> getComponentsOfBuild(Long buildId);
}
