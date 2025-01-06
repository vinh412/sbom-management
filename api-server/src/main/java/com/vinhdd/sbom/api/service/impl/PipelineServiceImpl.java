package com.vinhdd.sbom.api.service.impl;

import com.vinhdd.sbom.api.dto.PipelineDTO;
import com.vinhdd.sbom.api.dto.in.CreatePipelineDtoIn;
import com.vinhdd.sbom.api.dto.out.DependencyDtoOut;
import com.vinhdd.sbom.api.dto.queryout.BuildDtoQueryOut;
import com.vinhdd.sbom.api.dto.queryout.DetailComponentDtoQueryOut;
import com.vinhdd.sbom.api.exception.NotFoundException;
import com.vinhdd.sbom.api.model.*;
import com.vinhdd.sbom.api.repository.ComponentRepository;
import com.vinhdd.sbom.api.repository.PipelineRepository;
import com.vinhdd.sbom.api.repository.ProjectRepository;
import com.vinhdd.sbom.api.service.AuthService;
import com.vinhdd.sbom.api.service.BuildService;
import com.vinhdd.sbom.api.service.PipelineService;
import com.vinhdd.sbom.api.service.SbomService;
import com.vinhdd.sbom.api.util.helper.QueryResultMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PipelineServiceImpl implements PipelineService {
    private final QueryResultMapper queryResultMapper;
    private final BuildService buildService;
    private final PipelineRepository pipelineRepository;
    private final ProjectRepository projectRepository;
    private final AuthService authService;
    private final SbomService sbomService;
    private final ComponentRepository componentRepository;

    @Override
    @Transactional
    public List<PipelineDTO> getAllByProjectName(String projectName) {
        Project project = projectRepository.findByName(projectName).orElseThrow(() -> new NotFoundException("Project not found"));
        if (authService.hasAnyRole("SYS_ADMIN") || authService.isProjectAdmin(project.getId())) {
            return pipelineRepository.findAllByProjectName(projectName).stream()
                    .map(PipelineDTO::from).toList();
        }
        if (!authService.isMember(project.getId())) {
            throw new NotFoundException("Project not found");
        }
        String userId = authService.getCurrentUser().getId();
        return pipelineRepository.findAllByProjectNameAndUserId(projectName, userId).stream()
                .map(pipeline ->
                        queryResultMapper.mapResult(pipeline, PipelineDTO.class)).toList();

    }

    @Override
    public PipelineDTO create(String projectName, CreatePipelineDtoIn createPipelineDtoIn) {
        Project project = projectRepository.findByName(projectName).orElseThrow(() -> new NotFoundException("Project not found"));
        if (!authService.hasAnyRole("SYS_ADMIN") || !authService.isProjectAdmin(project.getId())) {
            throw new NotFoundException("Project not found");
        }
        Pipeline pipeline = new Pipeline();
        pipeline.setName(createPipelineDtoIn.getName());
        pipeline.setDescription(createPipelineDtoIn.getDescription());
        pipeline.setProject(project);
        return PipelineDTO.from(pipelineRepository.save(pipeline));
    }

    @Override
    public List<DependencyDtoOut> getDependenciesOfLatestBuild(String projectName, String pipelineName) {
        Pipeline pipeline = pipelineRepository.findByNameAndProjectName(pipelineName, projectName).orElseThrow(() -> new NotFoundException("Pipeline not found"));
        if (authService.hasPipelineAccess(pipeline.getId())) {
            throw new NotFoundException("Pipeline not found");
        }
        BuildDtoQueryOut buildDto = buildService.getLatestBuild(projectName, pipelineName);
        return sbomService.getDependenciesOfSbom(buildDto.getSbomId());
    }

    @Override
    public List<DetailComponentDtoQueryOut> getComponentsOfLatestBuild(String projectName, String pipelineName) {
        Pipeline pipeline = pipelineRepository.findByNameAndProjectName(pipelineName, projectName).orElseThrow(() -> new NotFoundException("Pipeline not found"));
        if (authService.hasPipelineAccess(pipeline.getId())) {
            throw new NotFoundException("Pipeline not found");
        }
        BuildDtoQueryOut buildDto = buildService.getLatestBuild(projectName, pipelineName);
        return componentRepository.getDetailComponentsOfBuild(buildDto.getId()).stream().map(
                component -> queryResultMapper.mapResult(component, DetailComponentDtoQueryOut.class)
        ).toList();
    }
}
