package com.vinhdd.sbom.api.service;

import com.vinhdd.sbom.api.dto.ProjectDTO;
import com.vinhdd.sbom.api.dto.ProjectWithPipelinesDTO;
import com.vinhdd.sbom.api.dto.in.PageRequestDtoIn;
import com.vinhdd.sbom.api.dto.in.ProjectDtoIn;
import org.springframework.data.domain.Page;

public interface ProjectService {
    Page<ProjectDTO> getAllProjects(PageRequestDtoIn pageRequestDtoIn);
    ProjectWithPipelinesDTO getProjectByName(String name);
    ProjectDTO createProject(ProjectDtoIn projectDtoIn);
    ProjectDTO updateProject(String projectId, ProjectDtoIn projectDtoIn);
    void deleteProject(String id);
}
