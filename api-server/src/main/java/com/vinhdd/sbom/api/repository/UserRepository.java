package com.vinhdd.sbom.api.repository;

import com.vinhdd.sbom.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    public User findByUsername(String username);
}
