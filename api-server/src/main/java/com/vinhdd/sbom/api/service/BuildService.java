package com.vinhdd.sbom.api.service;

import com.vinhdd.sbom.api.dto.BuildDto;
import com.vinhdd.sbom.api.dto.in.PageRequestDtoIn;
import com.vinhdd.sbom.api.dto.queryout.BuildDtoQueryOut;
import com.vinhdd.sbom.api.dto.sbomfile.SbomDto;
import org.springframework.data.domain.Page;

public interface BuildService {
    void createBuild(String projectName, String pipelineName, String buildNumber, SbomDto sbomDto);
    Page<BuildDto> getAllBuildsOfPipeline(String projectName, String pipelineName, PageRequestDtoIn pageRequestDtoIn);
    BuildDtoQueryOut getLatestBuild(String projectName, String pipelineName);
}
