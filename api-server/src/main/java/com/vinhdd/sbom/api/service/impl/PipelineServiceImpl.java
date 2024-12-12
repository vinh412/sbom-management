package com.vinhdd.sbom.api.service.impl;

import com.vinhdd.sbom.api.dto.PipelineDTO;
import com.vinhdd.sbom.api.dto.in.CreatePipelineDtoIn;
import com.vinhdd.sbom.api.dto.in.PageRequestDtoIn;
import com.vinhdd.sbom.api.dto.out.DependencyDtoOut;
import com.vinhdd.sbom.api.dto.queryout.BuildDtoQueryOut;
import com.vinhdd.sbom.api.dto.queryout.ComponentDtoQueryOut;
import com.vinhdd.sbom.api.dto.sbomfile.SbomDto;
import com.vinhdd.sbom.api.exception.NotFoundException;
import com.vinhdd.sbom.api.model.*;
import com.vinhdd.sbom.api.repository.BuildRepository;
import com.vinhdd.sbom.api.repository.ComponentRepository;
import com.vinhdd.sbom.api.repository.PipelineRepository;
import com.vinhdd.sbom.api.repository.ProjectRepository;
import com.vinhdd.sbom.api.service.BuildService;
import com.vinhdd.sbom.api.service.PipelineService;
import com.vinhdd.sbom.api.service.SbomService;
import com.vinhdd.sbom.api.util.helper.QueryResultMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PipelineServiceImpl implements PipelineService {
    private final QueryResultMapper queryResultMapper;
    private final BuildService buildService;
    private final PipelineRepository pipelineRepository;
    private final ProjectRepository projectRepository;
    private final BuildRepository buildRepository;
    private final SbomService sbomService;
    private final ComponentRepository componentRepository;

    @Override
    public Page<PipelineDTO> getAllByProjectName(String projectName, PageRequestDtoIn pageRequestDtoIn) {
        Sort sort = Sort.by(pageRequestDtoIn.getSortBy());
        if (pageRequestDtoIn.getOrder().equals("desc")) {
            sort = sort.descending();
        }
        Pageable pageable = PageRequest.of(pageRequestDtoIn.getPage() - 1, pageRequestDtoIn.getSize(), sort);
        return pipelineRepository.findAllByProjectNameAndNameContainingIgnoreCase(projectName, pageRequestDtoIn.getSearch(), pageable)
                .map(PipelineDTO::from);
    }

    @Override
    public PipelineDTO create(String projectName, CreatePipelineDtoIn createPipelineDtoIn) {
        Project project = projectRepository.findByName(projectName).orElseThrow(() -> new NotFoundException("Project not found"));
        Pipeline pipeline = new Pipeline();
        pipeline.setName(createPipelineDtoIn.getName());
        pipeline.setDescription(createPipelineDtoIn.getDescription());
        pipeline.setProject(project);
        return PipelineDTO.from(pipelineRepository.save(pipeline));
    }

    @Override
    public List<DependencyDtoOut> getDependenciesOfLatestBuild(String projectName, String pipelineName) {
        BuildDtoQueryOut buildDto = buildService.getLatestBuild(projectName, pipelineName);
        return sbomService.getDependenciesOfSbom(buildDto.getSbomId());
    }

    @Override
    public Set<ComponentDtoQueryOut> getComponentsOfLatestBuild(String projectName, String pipelineName) {
        BuildDtoQueryOut buildDto = buildService.getLatestBuild(projectName, pipelineName);

        return componentRepository.getComponentsOfBuild(buildDto.getId()).stream().map(
                component -> queryResultMapper.mapResult(component, ComponentDtoQueryOut.class)
        ).collect(Collectors.toSet());
    }
}
