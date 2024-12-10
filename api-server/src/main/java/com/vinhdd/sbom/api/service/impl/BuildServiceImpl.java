package com.vinhdd.sbom.api.service.impl;

import com.vinhdd.sbom.api.dto.BuildDto;
import com.vinhdd.sbom.api.dto.in.PageRequestDtoIn;
import com.vinhdd.sbom.api.dto.queryout.BuildDtoQueryOut;
import com.vinhdd.sbom.api.exception.NotFoundException;
import com.vinhdd.sbom.api.model.Pipeline;
import com.vinhdd.sbom.api.repository.BuildRepository;
import com.vinhdd.sbom.api.repository.PipelineRepository;
import com.vinhdd.sbom.api.service.BuildService;
import com.vinhdd.sbom.api.util.helper.QueryResultMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BuildServiceImpl implements BuildService {
    private final BuildRepository buildRepository;
    private final PipelineRepository pipelineRepository;
    private final QueryResultMapper queryResultMapper;

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
}
