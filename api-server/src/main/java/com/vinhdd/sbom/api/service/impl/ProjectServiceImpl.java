package com.vinhdd.sbom.api.service.impl;

import com.vinhdd.sbom.api.dto.out.PipelineDTO;
import com.vinhdd.sbom.api.dto.out.ProjectDTO;
import com.vinhdd.sbom.api.model.Project;
import com.vinhdd.sbom.api.repository.PipelineRepository;
import com.vinhdd.sbom.api.repository.ProjectRepository;
import com.vinhdd.sbom.api.service.ProjectService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final PipelineRepository pipelineRepository;

    @Override
    @Transactional
    public List<ProjectDTO> getAllProjects() {
        return projectRepository.findAll().stream()
                .map(project -> ProjectDTO.builder()
                        .id(project.getId())
                        .name(project.getName())
                        .pipelines(pipelineRepository.findAllByProjectId(project.getId()).stream()
                                .map(pipeline -> PipelineDTO.builder()
                                        .id(pipeline.getId())
                                        .name(pipeline.getName())
                                        .build())
                                .collect(Collectors.toList()))
                        .build())
                .toList();
    }

    @Override
    public ProjectDTO getProjectById(String id) {
        Project project = projectRepository.findById(id).orElseThrow();
        return ProjectDTO.builder()
                .id(project.getId())
                .name(project.getName())
                .pipelines(pipelineRepository.findAllByProjectId(project.getId())
                        .stream()
                        .map(pipeline -> PipelineDTO.builder()
                                .id(pipeline.getId())
                                .name(pipeline.getName())
                                .build())
                        .toList())
                .build();
    }

    @Override
    public ProjectDTO createProject(Project project) {
        Project savedProject = projectRepository.save(project);
        return ProjectDTO.builder()
                .id(savedProject.getId())
                .name(savedProject.getName())
                .build();
    }

    @Override
    @Transactional
    public ProjectDTO updateProject(Project updatedProject) {
        Project project = projectRepository.findById(updatedProject.getId()).orElseThrow();
        project.setName(updatedProject.getName());
        return ProjectDTO.builder()
                .id(project.getId())
                .name(project.getName())
                .build();
    }

    @Override
    public void deleteProject(String id) {
        projectRepository.deleteById(id);
    }
}
