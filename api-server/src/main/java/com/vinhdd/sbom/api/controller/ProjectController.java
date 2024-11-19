package com.vinhdd.sbom.api.controller;

import com.vinhdd.sbom.api.dto.out.ApiResponse;
import com.vinhdd.sbom.api.model.Project;
import com.vinhdd.sbom.api.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/projects")
public class ProjectController {
    private final ProjectService projectService;

    @GetMapping("")
    public ResponseEntity<?> getAllProjects() {
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .success(true)
                        .message("Get all projects successfully")
                        .data(projectService.getAllProjects())
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProjectById(@PathVariable String id) {
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .success(true)
                        .message("Get project successfully")
                        .data(projectService.getProjectById(id))
                        .build()
        );
    }

    @PostMapping("")
    public ResponseEntity<?> createProject(@RequestBody Project project) {
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .success(true)
                        .message("Create project successfully")
                        .data(projectService.createProject(project))
                        .build()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProject(@PathVariable String id, @RequestBody Project project) {
        project.setId(id);
        return ResponseEntity.ok().body(
                ApiResponse.builder()
                        .success(true)
                        .message("Update project successfully")
                        .data(projectService.updateProject(project))
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable String id) {
        projectService.deleteProject(id);
        return ResponseEntity.ok().body(
                ApiResponse.builder()
                        .success(true)
                        .message("Delete project successfully")
                        .build()
        );
    }
}
