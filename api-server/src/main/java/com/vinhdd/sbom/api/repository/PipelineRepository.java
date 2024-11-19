package com.vinhdd.sbom.api.repository;

import com.vinhdd.sbom.api.model.Pipeline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PipelineRepository extends JpaRepository<Pipeline, String> {
    List<Pipeline> findAllByProjectId(String projectId);
}
