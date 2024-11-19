package com.vinhdd.sbom.api.repository;

import com.vinhdd.sbom.api.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, String> {
    void deleteById(@NonNull String id);
}
