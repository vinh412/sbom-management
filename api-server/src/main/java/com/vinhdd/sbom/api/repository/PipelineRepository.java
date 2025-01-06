package com.vinhdd.sbom.api.repository;

import com.vinhdd.sbom.api.model.Pipeline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface PipelineRepository extends JpaRepository<Pipeline, String> {
    @Query(value = "SELECT p.id, p.created_at, p.name, p.project_id, p.updated_at, p.description, p.branch FROM memberships m " +
            "JOIN projects prj ON m.project_id = prj.id " +
            "JOIN member_pipeline mp ON m.id = mp.membership_id " +
            "JOIN pipelines p ON p.id = mp.pipeline_id " +
            "WHERE prj.name = ?1 AND m.user_id = ?2", nativeQuery = true)
    List<Map<String, Object>> findAllByProjectNameAndUserId(String projectName, String userId);
    List<Pipeline> findAllByProjectName(String projectName);
    Optional<Pipeline> findByNameAndProjectName(String name, String projectName);
}
