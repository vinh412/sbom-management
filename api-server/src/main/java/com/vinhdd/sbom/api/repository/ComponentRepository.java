package com.vinhdd.sbom.api.repository;

import com.vinhdd.sbom.api.model.Component;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface ComponentRepository extends JpaRepository<Component, Long> {
    Component findByPurl(String purl);
    Component findByBomRef(String bomRef);

    @Query(value = "SELECT c.purl AS ref, COALESCE(json_agg(d.purl) FILTER ( WHERE d.purl IS NOT NULL ), '[]') AS depends_on " +
            "FROM components c " +
            "LEFT JOIN component_dependency cd on c.id = cd.component_id " +
            "LEFT JOIN components d on cd.dependency_id = d.id " +
            "WHERE c.purl = ?1 GROUP BY c.purl", nativeQuery = true)
    Map<String, Object> getDirectDependenciesOfComponent(String purl);

    @Query(value = "SELECT c.*, COALESCE(json_agg(json_build_object('licenseId', l.license_id, 'isDeprecated', l.is_deprecated)) FILTER ( WHERE l.license_id IS NOT NULL ), '[]') as licenses, " +
            "CASE WHEN c.version < c.latest_version THEN false ELSE true END AS is_latest, " +
            "COALESCE(json_agg(json_build_object('vulId', v.vul_id, 'cvssScore', v.cvss_score)) FILTER ( WHERE v.id IS NOT NULL ), '[]') as vulnerabilities, " +
            "SUM(CASE WHEN v.cvss_score >= 9.0 THEN 10 WHEN v.cvss_score >= 7.0 THEN 5 WHEN v.cvss_score >= 4.0 THEN 3 WHEN v.cvss_score >= 0.1 THEN 1 ELSE 0 END + CASE WHEN l.is_deprecated = TRUE THEN 0.5 ELSE 0 END + CASE WHEN c.version < c.latest_version THEN 0.3 ELSE 0 END) as score " +
            "FROM components c " +
            "JOIN sbom_component sc on c.id = sc.component_id " +
            "JOIN sbom s on sc.sbom_id = s.id " +
            "JOIN builds b on s.id = b.sbom_id " +
            "LEFT JOIN component_license cl ON c.id = cl.component_id " +
            "LEFT JOIN licenses l ON cl.license_id = l.id " +
            "LEFT JOIN component_vulnerability cv ON c.id = cv.component_id " +
            "LEFT JOIN vulnerabilities v ON cv.vulnerability_id = v.id " +
            "WHERE b.id = ?1 GROUP BY c.id ORDER BY score DESC", nativeQuery = true)
    List<Map<String, Object>> getDetailComponentsOfBuild(Long buildId);

    @Query(value = "SELECT c.id, c.purl, c.name, c.group_name, c.publisher, c.description, c.version " +
            "FROM components c " +
            "JOIN sbom_component sc ON c.id = sc.component_id " +
            "JOIN builds b ON sc.sbom_id = b.sbom_id WHERE b.id = ?1", nativeQuery = true)
    List<Map<String, Object>> getComponentsOfBuild(Long buildId);


    @Query(value = "SELECT c.*, COALESCE(json_agg(json_build_object('pipelineName', p.name, 'projectName', pr.name)) FILTER ( WHERE p.id IS NOT NULL ), '[]') as appear_in FROM components c " +
            "JOIN sbom_component sc ON c.id = sc.component_id " +
            "JOIN builds b ON sc.sbom_id = b.sbom_id " +
            "JOIN pipelines p ON b.pipeline_id = p.id " +
            "JOIN projects pr ON pr.id = p.project_id " +
            "JOIN memberships ON pr.id = memberships.project_id " +
            "WHERE 'SYS_ADMIN' IN (SELECT r.name FROM roles r JOIN user_role ur ON r.id = ur.role_id JOIN users u ON u.id = ur.user_id WHERE ur.user_id = ?1) OR memberships.user_id = ?1 GROUP BY c.id, p.id", nativeQuery = true)
    List<Map<String, Object>> getComponents(String userId);

}
