package com.vinhdd.sbom.api.controller;

import com.vinhdd.sbom.api.dto.in.RoleDtoIn;
import com.vinhdd.sbom.api.dto.out.ApiResponse;
import com.vinhdd.sbom.api.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @GetMapping("")
    public ResponseEntity<?> getAllRoles() {
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .success(true)
                        .message("Get all roles successfully")
                        .data(roleService.getAll())
                        .build()
        );
    }

    @PostMapping("")
    public ResponseEntity<?> createRole(@RequestBody RoleDtoIn roleDtoIn) {
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .success(true)
                        .message("Create role successfully")
                        .data(roleService.create(roleDtoIn))
                        .build()
        );
    }

    @PutMapping("")
    public ResponseEntity<?> updateRole(@RequestBody RoleDtoIn roleDtoIn) {
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .success(true)
                        .message("Update role successfully")
                        .data(roleService.update(roleDtoIn))
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable Long id) {
        roleService.delete(id);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .success(true)
                        .message("Delete role successfully")
                        .build()
        );
    }
}
