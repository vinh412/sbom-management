package com.vinhdd.sbom.api.repository;

import com.vinhdd.sbom.api.model.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, String> {
    Optional<Project> findByName(String name);
    Page<Project> findAllByNameContainingIgnoreCase(String name, Pageable pageable);
    void deleteById(@NonNull String id);
    @Query(value = "SELECT u.id, u.username, u.fullname, u.email FROM users u " +
            "WHERE u.id NOT IN (SELECT m.user_id FROM memberships m WHERE m.project_id = (SELECT p.id FROM projects p WHERE p.name = ?1)) " +
            "AND u.id NOT IN (SELECT ur.user_id FROM user_role ur WHERE ur.role_id = (SELECT r.id FROM roles r WHERE r.name = 'SYS_ADMIN'))", nativeQuery = true)
    List<Map<String, Object>> getAllUsersNotInProject(String projectName);
}
