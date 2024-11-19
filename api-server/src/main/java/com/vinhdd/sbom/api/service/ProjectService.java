package com.vinhdd.sbom.api.service;

import com.vinhdd.sbom.api.dto.out.ProjectDTO;
import com.vinhdd.sbom.api.model.Project;

import java.util.List;

public interface ProjectService {
    List<ProjectDTO> getAllProjects();
    ProjectDTO getProjectById(String id);
    ProjectDTO createProject(Project project);
    ProjectDTO updateProject(Project project);
    void deleteProject(String id);
}
