package com.vinhdd.sbom.api.service;

import com.vinhdd.sbom.api.dto.PipelineDTO;
import com.vinhdd.sbom.api.dto.in.CreatePipelineDtoIn;
import com.vinhdd.sbom.api.dto.in.PageRequestDtoIn;
import com.vinhdd.sbom.api.dto.out.DependencyDtoOut;
import com.vinhdd.sbom.api.dto.queryout.ComponentDtoQueryOut;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

public interface PipelineService {
    Page<PipelineDTO> getAllByProjectName(String projectName, PageRequestDtoIn pageRequestDtoIn);
    PipelineDTO create(String projectName, CreatePipelineDtoIn createPipelineDtoIn);
    List<DependencyDtoOut> getDependenciesOfLatestBuild(String projectName, String pipelineName);
    Set<ComponentDtoQueryOut> getComponentsOfLatestBuild(String projectName, String pipelineName);
}
