package com.vinhdd.sbom.api.repository;

import com.vinhdd.sbom.api.model.Build;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuildRepository extends JpaRepository<Build, Integer> {
}
