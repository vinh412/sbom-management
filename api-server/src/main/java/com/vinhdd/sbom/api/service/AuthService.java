package com.vinhdd.sbom.api.service;

import com.vinhdd.sbom.api.dto.out.AuthResponse;

public interface AuthService {
    AuthResponse login(String username, String password);
    void register(String username, String password);
}