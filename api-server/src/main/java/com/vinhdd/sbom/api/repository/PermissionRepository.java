package com.vinhdd.sbom.api.repository;

import com.vinhdd.sbom.api.model.Permission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, String> {
    Page<Permission> findAllByIdContaining(String id, Pageable pageable);
}
