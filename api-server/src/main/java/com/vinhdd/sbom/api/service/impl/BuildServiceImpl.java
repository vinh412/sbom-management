package com.vinhdd.sbom.api.service.impl;

import com.vinhdd.sbom.api.dto.BuildDto;
import com.vinhdd.sbom.api.dto.in.PageRequestDtoIn;
import com.vinhdd.sbom.api.dto.out.CompareBuildDto;
import com.vinhdd.sbom.api.dto.queryout.BuildDtoQueryOut;
import com.vinhdd.sbom.api.dto.queryout.ComponentDtoQueryOut;
import com.vinhdd.sbom.api.dto.sbomfile.ComponentDto;
import com.vinhdd.sbom.api.dto.sbomfile.SbomDto;
import com.vinhdd.sbom.api.exception.NotFoundException;
import com.vinhdd.sbom.api.model.Build;
import com.vinhdd.sbom.api.model.Pipeline;
import com.vinhdd.sbom.api.model.Sbom;
import com.vinhdd.sbom.api.repository.BuildRepository;
import com.vinhdd.sbom.api.repository.ComponentRepository;
import com.vinhdd.sbom.api.repository.PipelineRepository;
import com.vinhdd.sbom.api.repository.SbomRepository;
import com.vinhdd.sbom.api.service.BuildService;
import com.vinhdd.sbom.api.service.SbomService;
import com.vinhdd.sbom.api.util.helper.QueryResultMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BuildServiceImpl implements BuildService {
    private final BuildRepository buildRepository;
    private final SbomService sbomService;
    private final SbomRepository sbomRepository;
    private final PipelineRepository pipelineRepository;
    private final QueryResultMapper queryResultMapper;
    private final ComponentRepository componentRepository;

    @Override
    @Transactional
    public void createBuild(String projectName, String pipelineName, int buildNumber, String result, long duration, LocalDateTime startAt, SbomDto sbomDto) {
        Pipeline pipeline = pipelineRepository.findByNameAndProjectName(pipelineName, projectName)
                .orElseThrow(() -> new NotFoundException("Pipeline not found"));
        Sbom sbom;
        if (!result.equals("SUCCESS")) {
            sbom = getSbomByLatestBuild(projectName, pipelineName);
        } else {
            sbom = sbomService.save(sbomDto);
        }
        Build build = new Build();
        build.setNumber(buildNumber);
        build.setResult(result);
        build.setDuration(duration);
        build.setStartAt(startAt);
        build.setPipeline(pipeline);
        build.setSbom(sbom);
        buildRepository.save(build);
    }

    @Override
    @Transactional
    public Page<BuildDto> getAllBuildsOfPipeline(String projectName, String pipelineName, PageRequestDtoIn pageRequestDtoIn) {
        Pipeline pipeline = pipelineRepository.findByNameAndProjectName(pipelineName, projectName)
                .orElseThrow(() -> new NotFoundException("Pipeline not found"));
        Sort sort = Sort.by(pageRequestDtoIn.getSortBy());
        if (pageRequestDtoIn.getOrder().equals("desc")) {
            sort = sort.descending();
        }
        Pageable pageable = PageRequest.of(pageRequestDtoIn.getPage() - 1, pageRequestDtoIn.getSize(), sort);
        return buildRepository.findAllByPipelineId(pipeline.getId(), pageable)
                .map(BuildDto::fromEntity);
    }

    @Override
    public BuildDtoQueryOut getLatestBuild(String projectName, String pipelineName) {
        return queryResultMapper.mapResult(buildRepository.findLatestBuild(projectName, pipelineName), BuildDtoQueryOut.class);
    }

    @Override
    public CompareBuildDto compareBuilds(Long buildId1, Long buildId2) {
        List<ComponentDtoQueryOut> components1 = getComponentsOfBuild(buildId1);
        List<ComponentDtoQueryOut> components2 = getComponentsOfBuild(buildId2);
        CompareBuildDto result = new CompareBuildDto();
        for (int i = components1.size() - 1; i >= 0; i--) {
            for (int j = components2.size() - 1; j >= 0; j--) {
                ComponentDtoQueryOut component1 = components1.get(i);
                ComponentDtoQueryOut component2 = components2.get(j);
                if (component1.equals(component2)) {
                    result.getUnchangedComponents().add(component1);
                    components1.remove(component1);
                    components2.remove(component2);
                    break;
                } else if (component1.equalsExcludeVersion(component2)) {
                    component1.setVersion(component1.getVersion() + " -> " + component2.getVersion());
                    if (component1.getVersion().compareTo(component2.getVersion()) > 0) {
                        result.getUpgradedComponents().add(component1);
                    } else {
                        result.getDowngradedComponents().add(component1);
                    }
                    components1.remove(component1);
                    components2.remove(component2);
                    break;
                }
            }
        }
        result.getInsertedComponents().addAll(components1);
        result.getRemovedComponents().addAll(components2);
        return result;
    }

    @Override
    public List<ComponentDtoQueryOut> getComponentsOfBuild(Long buildId) {
        return componentRepository.getComponentsOfBuild(buildId).stream().map(
                component -> queryResultMapper.mapResult(component, ComponentDtoQueryOut.class)
        ).collect(Collectors.toList());
    }

    private Sbom getSbomByLatestBuild(String projectName, String pipelineName) {
        return sbomRepository.findById(getLatestBuild(projectName, pipelineName).getSbomId()).orElse(null);
    }
}
