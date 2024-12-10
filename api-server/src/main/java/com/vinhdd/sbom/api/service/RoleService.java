package com.vinhdd.sbom.api.service;

import com.vinhdd.sbom.api.dto.RoleDto;
import com.vinhdd.sbom.api.dto.in.RoleDtoIn;

import java.util.Set;

public interface RoleService {
    Set<RoleDto> getAll();
    RoleDto create(RoleDtoIn roleDtoIn);
    RoleDto update(RoleDtoIn roleDtoIn);
    void delete(Long id);
}
