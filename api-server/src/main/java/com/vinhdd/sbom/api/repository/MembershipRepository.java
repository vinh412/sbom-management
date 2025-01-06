package com.vinhdd.sbom.api.repository;

import com.vinhdd.sbom.api.model.Membership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MembershipRepository extends JpaRepository<Membership, String> {
    List<Membership> findAllByProjectName(String projectName);
    List<Membership> findAllByUserId(String userId);
    Optional<Membership> findByProjectIdAndUserId(String projectId, String userId);
}
