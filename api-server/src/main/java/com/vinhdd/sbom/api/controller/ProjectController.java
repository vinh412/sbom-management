package com.vinhdd.sbom.api.controller;

import com.vinhdd.sbom.api.dto.in.PageRequestDtoIn;
import com.vinhdd.sbom.api.dto.in.ProjectDtoIn;
import com.vinhdd.sbom.api.dto.out.ApiResponse;
import com.vinhdd.sbom.api.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/projects")
public class ProjectController {
    private final ProjectService projectService;

    @GetMapping("")
    public ResponseEntity<?> getAllProjects(PageRequestDtoIn pageRequestDtoIn) {
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .success(true)
                        .message("Get all projects successfully")
                        .data(new PagedModel<>(projectService.getAllProjects(pageRequestDtoIn)))
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProjectByName(@PathVariable String id) {
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .success(true)
                        .message("Get project successfully")
                        .data(projectService.getProjectByName(id))
                        .build()
        );
    }

    @PostMapping("")
    public ResponseEntity<?> createProject(@RequestBody ProjectDtoIn projectDtoIn) {
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .success(true)
                        .message("Create project successfully")
                        .data(projectService.createProject(projectDtoIn))
                        .build()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProject(@PathVariable String id, @RequestBody ProjectDtoIn projectDtoIn) {
        return ResponseEntity.ok().body(
                ApiResponse.builder()
                        .success(true)
                        .message("Update project successfully")
                        .data(projectService.updateProject(id, projectDtoIn))
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
