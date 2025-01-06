package com.vinhdd.sbom.api.service;

import com.vinhdd.sbom.api.dto.PermissionDto;

import java.util.List;


public interface PermissionService {
    List<PermissionDto> getAll();
}
