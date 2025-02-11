package com.vinhdd.sbom.api.service;

import com.vinhdd.sbom.api.dto.BuildDto;
import com.vinhdd.sbom.api.dto.in.PageRequestDtoIn;
import com.vinhdd.sbom.api.dto.out.CompareBuildDto;
import com.vinhdd.sbom.api.dto.out.DependencyDtoOut;
import com.vinhdd.sbom.api.dto.queryout.BuildDtoQueryOut;
import com.vinhdd.sbom.api.dto.queryout.ComponentDtoQueryOut;
import com.vinhdd.sbom.api.dto.queryout.DetailComponentDtoQueryOut;
import com.vinhdd.sbom.api.dto.sbomfile.SbomDto;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

public interface BuildService {
    void createBuild(String projectName, String pipelineName, String repository, String branch, int buildNumber, String result, long duration, LocalDateTime startAt, SbomDto sbomDto);
    Page<BuildDto> getAllBuildsOfPipeline(String projectName, String pipelineName, PageRequestDtoIn pageRequestDtoIn);
    BuildDtoQueryOut getLatestBuild(String projectName, String pipelineName);
    CompareBuildDto compareBuilds(Long buildId1, Long buildId2);
    List<ComponentDtoQueryOut> getComponentsOfBuild(Long buildId);
    List<DetailComponentDtoQueryOut> getDetailComponentsByBuildId(Long buildId);
    List<DependencyDtoOut> getDependenciesByBuildId(Long buildId);
}
