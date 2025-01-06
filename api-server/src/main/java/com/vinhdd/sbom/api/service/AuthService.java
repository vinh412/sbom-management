package com.vinhdd.sbom.api.service;

import com.vinhdd.sbom.api.dto.out.AuthResponse;
import com.vinhdd.sbom.api.model.User;

public interface AuthService {
    AuthResponse login(String username, String password);
    void register(String username, String password);
    boolean hasAnyRole(String... roles);
    User getCurrentUser();
    boolean isMember(String projectId);
    boolean isProjectAdmin(String projectId);
    boolean hasPipelineAccess(String pipelineId);
}