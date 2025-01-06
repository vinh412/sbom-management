package com.vinhdd.sbom.api.service;

import com.vinhdd.sbom.api.dto.PipelineDTO;
import com.vinhdd.sbom.api.dto.in.CreatePipelineDtoIn;
import com.vinhdd.sbom.api.dto.out.DependencyDtoOut;
import com.vinhdd.sbom.api.dto.queryout.DetailComponentDtoQueryOut;

import java.util.List;

public interface PipelineService {
    List<PipelineDTO> getAllByProjectName(String projectName);
    PipelineDTO create(String projectName, CreatePipelineDtoIn createPipelineDtoIn);
    List<DependencyDtoOut> getDependenciesOfLatestBuild(String projectName, String pipelineName);
    List<DetailComponentDtoQueryOut> getComponentsOfLatestBuild(String projectName, String pipelineName);
}
