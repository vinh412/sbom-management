package com.vinhdd.sbom.api.repository;

import com.vinhdd.sbom.api.model.Sbom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SbomRepository extends JpaRepository<Sbom, Integer> {
}
