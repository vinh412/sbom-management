package com.vinhdd.sbom.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vinhdd.sbom.api.dto.in.CreatePipelineDtoIn;
import com.vinhdd.sbom.api.dto.in.PageRequestDtoIn;
import com.vinhdd.sbom.api.dto.out.ApiResponse;
import com.vinhdd.sbom.api.service.BuildService;
import com.vinhdd.sbom.api.service.PipelineService;
import com.vinhdd.sbom.api.service.SbomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/projects/{projectName}/pipelines")
public class PipelineController {
    private final ObjectMapper objectMapper;
    private final PipelineService pipelineService;
    private final SbomService sbomService;
    private final BuildService buildService;

    @GetMapping("")
    public ResponseEntity<?> getAllPipelinesOfProject(@PathVariable("projectName") String projectName,
                                                      PageRequestDtoIn pageRequestDtoIn) {
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .success(true)
                        .message("Pipelines retrieved successfully")
                        .data(new PagedModel<>(pipelineService.getAllByProjectName(projectName, pageRequestDtoIn)))
                        .build()
        );
    }

    @PostMapping("")
    public ResponseEntity<?> createPipeline(@PathVariable("projectName") String projectName,
                                            @RequestBody CreatePipelineDtoIn pipeline) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.builder()
                        .success(true)
                        .message("Pipeline created successfully")
                        .data(pipelineService.create(projectName, pipeline))
                        .build()
        );
    }

    @GetMapping("/{pipelineName}/builds")
    public ResponseEntity<?> getAllBuilds(@PathVariable("projectName") String projectName,
                                      @PathVariable("pipelineName") String pipelineName,
                                      PageRequestDtoIn pageRequestDtoIn) {
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .success(true)
                        .message("Builds retrieved successfully")
                        .data(new PagedModel<>(buildService.getAllBuildsOfPipeline(projectName, pipelineName, pageRequestDtoIn)))
                        .build()
        );
    }

    @GetMapping("/{pipelineName}/builds/{buildNumber}")
    public ResponseEntity<?> getBuild(@PathVariable("projectName") String projectName,
                                      @PathVariable("pipelineName") String pipelineName,
                                      @PathVariable("buildNumber") String buildNumber) {
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .success(true)
                        .message("Build retrieved successfully")
//                        .data(buildService.getBuild(projectName, pipelineName, buildNumber))
                        .build()
        );
    }

    @GetMapping("/{pipelineName}/builds/latest/dependencies")
    public ResponseEntity<?> getDependenciesOfLatestBuild(@PathVariable("projectName") String projectName,
                                                        @PathVariable("pipelineName") String pipelineName) {

        return ResponseEntity.ok(
                ApiResponse.builder()
                        .success(true)
                        .message("Dependencies retrieved successfully")
                        .data(pipelineService.getDependenciesOfLatestBuild(projectName, pipelineName))
                        .build()
        );
    }

    @GetMapping("/{pipelineName}/builds/latest/components")
    public ResponseEntity<?> getComponentsOfLatestBuild(@PathVariable("projectName") String projectName,
                                                        @PathVariable("pipelineName") String pipelineName) {

        return ResponseEntity.ok(
                ApiResponse.builder()
                        .success(true)
                        .message("Components retrieved successfully")
                        .data(pipelineService.getComponentsOfLatestBuild(projectName, pipelineName))
                        .build()
        );
    }

}
