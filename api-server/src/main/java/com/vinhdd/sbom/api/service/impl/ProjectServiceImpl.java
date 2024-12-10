package com.vinhdd.sbom.api.service.impl;

import com.vinhdd.sbom.api.dto.ProjectDTO;
import com.vinhdd.sbom.api.dto.ProjectWithPipelinesDTO;
import com.vinhdd.sbom.api.dto.in.PageRequestDtoIn;
import com.vinhdd.sbom.api.dto.in.ProjectDtoIn;
import com.vinhdd.sbom.api.exception.NotFoundException;
import com.vinhdd.sbom.api.model.Project;
import com.vinhdd.sbom.api.repository.ProjectRepository;
import com.vinhdd.sbom.api.service.ProjectService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;

    @Override
    @Transactional
    public Page<ProjectDTO> getAllProjects(PageRequestDtoIn pageRequestDtoIn) {
        Sort sort = Sort.by(pageRequestDtoIn.getSortBy());
        if(pageRequestDtoIn.getOrder().equals("desc")) {
            sort = sort.descending();
        }
        Pageable pageable = PageRequest.of(pageRequestDtoIn.getPage() -1, pageRequestDtoIn.getSize(), sort);
        return projectRepository.findAllByNameContainingIgnoreCase(pageRequestDtoIn.getSearch(), pageable)
                .map(ProjectDTO::from);
    }

    @Override
    @Transactional
    public ProjectWithPipelinesDTO getProjectByName(String name) {
        Project project = projectRepository.findByName(name).orElseThrow(() -> new NotFoundException("Project not found"));
        return ProjectWithPipelinesDTO.from(project);
    }

    @Override
    public ProjectDTO createProject(ProjectDtoIn projectDtoIn) {
        Project project = new Project();
        project.setName(projectDtoIn.getName());
        project.setDescription(projectDtoIn.getDescription());
        return ProjectDTO.from(projectRepository.save(project));
    }

    @Override
    public ProjectDTO updateProject(String projectId, ProjectDtoIn projectDtoIn) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new NotFoundException("Project not found"));
        project.setName(projectDtoIn.getName());
        project.setDescription(projectDtoIn.getDescription());
        return ProjectDTO.from(projectRepository.save(project));
    }

    @Override
    public void deleteProject(String id) {
        projectRepository.deleteById(id);
    }
}
