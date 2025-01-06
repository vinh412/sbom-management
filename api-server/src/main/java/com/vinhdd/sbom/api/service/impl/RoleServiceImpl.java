package com.vinhdd.sbom.api.service.impl;

import com.vinhdd.sbom.api.dto.RoleDto;
import com.vinhdd.sbom.api.dto.in.RoleDtoIn;
import com.vinhdd.sbom.api.exception.BadRequestException;
import com.vinhdd.sbom.api.model.Permission;
import com.vinhdd.sbom.api.model.Role;
import com.vinhdd.sbom.api.repository.PermissionRepository;
import com.vinhdd.sbom.api.repository.RoleRepository;
import com.vinhdd.sbom.api.service.RoleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    @Override
    public Set<RoleDto> getAll() {
        return roleRepository.findAll().stream()
                .map(RoleDto::from)
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public RoleDto create(RoleDtoIn roleDtoIn) {
        try {
            Role role = new Role();
            role.setId(roleDtoIn.getId());
            role.setName(roleDtoIn.getName());
            Set<Permission> permissions = new HashSet<>();
            for (String permission : roleDtoIn.getPermissionIds()) {
                Permission p = permissionRepository.findById(permission)
                        .orElseThrow(() -> new BadRequestException("Permission not found: " + permission));
                permissions.add(p);
            }
            role.setIsDefault(false);
            role.setPermissions(permissions);
            return RoleDto.from(roleRepository.save(role));
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException("Role name already exists");
        }
    }

    @Override
    @Transactional
    public RoleDto update(Long roleId, RoleDtoIn roleDtoIn) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found" + roleDtoIn.getId()));
        if(role.getIsDefault()) {
            throw new BadRequestException("Cannot update default role");
        }
        role.setName(roleDtoIn.getName());
        role.getPermissions().clear();
        for (String permissionId : roleDtoIn.getPermissionIds()) {
            Permission p = permissionRepository.findById(permissionId)
                    .orElseThrow(() -> new BadRequestException("Permission not found: " + permissionId));
            role.getPermissions().add(p);
        }
        return RoleDto.from(roleRepository.save(role));
    }

    @Override
    public void delete(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found" + id));
        if(role.getIsDefault()) {
            throw new BadRequestException("Cannot delete default role");
        }
        roleRepository.deleteById(id);
    }
}
