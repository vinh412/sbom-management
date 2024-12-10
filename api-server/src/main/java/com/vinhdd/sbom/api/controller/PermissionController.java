package com.vinhdd.sbom.api.controller;

import com.vinhdd.sbom.api.dto.in.PageRequestDtoIn;
import com.vinhdd.sbom.api.dto.out.ApiResponse;
import com.vinhdd.sbom.api.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/permissions")
public class PermissionController {
    private final PermissionService permissionService;

    @GetMapping("")
    @PreAuthorize("hasAuthority('VIEW_PERMISSIONS')")
    public ResponseEntity<?> getAllPermissions(PageRequestDtoIn pageRequestDtoIn) {
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .success(true)
                        .message("Get all permissions successfully")
                        .data(new PagedModel<>(permissionService.getAll(pageRequestDtoIn)))
                        .build()
        );
    }
}
