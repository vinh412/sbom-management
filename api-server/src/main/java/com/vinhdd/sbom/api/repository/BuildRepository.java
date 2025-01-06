package com.vinhdd.sbom.api.repository;
import com.vinhdd.sbom.api.model.Build;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BuildRepository extends JpaRepository<Build, Long> {
    Page<Build> findAllByPipelineId(String pipelineId, Pageable pageable);

    @Query(value = "SELECT b.id, b.number, b.pipeline_id, b.sbom_id, b.start_at, b.duration, b.result FROM builds b " +
            "JOIN pipelines p ON b.pipeline_id = p.id " +
            "JOIN projects pr ON p.project_id = pr.id " +
            "WHERE pr.name = ?1 AND p.name = ?2 ORDER BY b.created_at DESC LIMIT 1", nativeQuery = true)
    Map<String, Object> findLatestBuild(String projectName, String pipelineName);
}
