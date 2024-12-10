package com.vinhdd.sbom.api.repository;

import com.vinhdd.sbom.api.model.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, String> {
    Optional<Project> findByName(String name);
    Page<Project> findAllByNameContainingIgnoreCase(String name, Pageable pageable);
    void deleteById(@NonNull String id);
}
