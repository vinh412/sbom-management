package com.vinhdd.sbom.api.repository;

import com.vinhdd.sbom.api.model.License;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface LicenseRepository extends JpaRepository<License, Long> {
    License findByLicenseId(String licenseId);
    @Query("SELECT l FROM License l WHERE LOWER(l.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(l.licenseId) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<License> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
