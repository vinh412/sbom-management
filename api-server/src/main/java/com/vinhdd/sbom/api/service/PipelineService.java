package com.vinhdd.sbom.api.service;

import com.vinhdd.sbom.api.dto.PipelineDTO;
import com.vinhdd.sbom.api.dto.in.CreatePipelineDtoIn;
import com.vinhdd.sbom.api.dto.in.PageRequestDtoIn;
import com.vinhdd.sbom.api.dto.out.DependencyDtoOut;
import com.vinhdd.sbom.api.dto.queryout.ComponentDtoQueryOut;
import com.vinhdd.sbom.api.dto.sbomfile.SbomDto;
import com.vinhdd.sbom.api.model.Component;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

public interface PipelineService {
    Page<PipelineDTO> getAllByProjectName(String projectName, PageRequestDtoIn pageRequestDtoIn);
    PipelineDTO create(String projectName, CreatePipelineDtoIn createPipelineDtoIn);
    void uploadSbom(String projectName, String pipelineName, String buildNumber, SbomDto sbomDto);
    List<DependencyDtoOut> getLatestBuildDependencies(String projectName, String pipelineName);
    Set<ComponentDtoQueryOut> getLatestBuildComponents(String projectName, String pipelineName);
}
