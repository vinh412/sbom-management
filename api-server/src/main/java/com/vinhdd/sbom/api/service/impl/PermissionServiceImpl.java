package com.vinhdd.sbom.api.service.impl;

import com.vinhdd.sbom.api.dto.PermissionDto;
import com.vinhdd.sbom.api.repository.PermissionRepository;
import com.vinhdd.sbom.api.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {
    private final PermissionRepository permissionRepository;

    @Override
    public List<PermissionDto> getAll() {
        return permissionRepository.findAll().stream()
                .map(PermissionDto::from).toList();
    }
}
