package com.vinhdd.sbom.api.repository;

import com.vinhdd.sbom.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {
    User findByUsername(String username);
    List<User> findAll();
}
