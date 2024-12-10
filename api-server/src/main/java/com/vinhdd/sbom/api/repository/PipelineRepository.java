package com.vinhdd.sbom.api.repository;

import com.vinhdd.sbom.api.model.Pipeline;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PipelineRepository extends JpaRepository<Pipeline, String> {
    Page<Pipeline> findAllByProjectNameAndNameContainingIgnoreCase(String projectName, String name, Pageable pageable);
    Optional<Pipeline> findByNameAndProjectName(String name, String projectName);
}
