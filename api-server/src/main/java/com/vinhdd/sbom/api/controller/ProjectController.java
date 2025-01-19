package com.vinhdd.sbom.api.controller;

import com.vinhdd.sbom.api.dto.in.AddMemberDtoIn;
import com.vinhdd.sbom.api.dto.in.ProjectDtoIn;
import com.vinhdd.sbom.api.dto.in.UpdateMemberDtoIn;
import com.vinhdd.sbom.api.dto.out.ApiResponse;
import com.vinhdd.sbom.api.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{name}")
    public ResponseEntity<?> getProjectByName(@PathVariable String name) {
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .success(true)
                        .message("Get project successfully")
                        .data(projectService.getProjectByName(name))
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

    @GetMapping("/{name}/members")
    public ResponseEntity<?> getMembers(@PathVariable String name) {
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Members retrieved successfully")
                        .success(true)
                        .data(projectService.getAllMembers(name))
                        .build()
        );
    }

    @PostMapping("/members")
    public ResponseEntity<?> addMember(@RequestBody @Valid AddMemberDtoIn dto) {
        projectService.addMember(dto.getProjectName(), dto.getUserId(), dto.getIsAdmin(), dto.getPipelineIds());
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Add member successfully")
                        .success(true)
                        .build()
        );
    }

    @DeleteMapping("/{name}/members/{userId}")
    public ResponseEntity<?> removeMember(@PathVariable String name, @PathVariable String userId) {
        projectService.removeMember(name, userId);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Remove member successfully")
                        .success(true)
                        .build()
        );
    }

    @PatchMapping("/{name}/members/{userId}")
    public ResponseEntity<?> updateMemberPermissions(@PathVariable String name, @PathVariable String userId, @RequestBody UpdateMemberDtoIn dto) {
        projectService.updateMemberPipeline(name, userId, dto.getIsAdmin(), dto.getPipelineIds());
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Update member permissions successfully")
                        .success(true)
                        .build()
        );
    }

    @GetMapping("/{name}/users-not-in")
    public ResponseEntity<?> getAllUsersNotInProject(@PathVariable String name) {
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .success(true)
                        .message("Get all users not in project successfully")
                        .data(projectService.getAllUsersNotInProject(name))
                        .build()
        );
    }

    @GetMapping("/{name}/membership")
    public ResponseEntity<?> getMembership(@PathVariable String name) {
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .success(true)
                        .message("Get all membership successfully")
                        .data(projectService.getMembership(name))
                        .build()
        );
    }
}
