package com.vinhdd.sbom.api.controller;

import com.vinhdd.sbom.api.dto.out.ApiResponse;
import com.vinhdd.sbom.api.service.ComponentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/components")
public class ComponentController {
    private final ComponentService componentService;

    public ComponentController(ComponentService componentService) {
        this.componentService = componentService;
    }

    @GetMapping("")
    public ResponseEntity<?> getAllComponents() {
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .message("Get all components successfully")
                .data(componentService.getComponents())
                .build());
    }
}
