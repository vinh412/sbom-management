package com.vinhdd.sbom.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vinhdd.sbom.api.dto.out.ApiResponse;
import com.vinhdd.sbom.api.dto.sbomfile.SbomDto;
import com.vinhdd.sbom.api.exception.BadRequestException;
import com.vinhdd.sbom.api.service.BuildService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

@Slf4j
@RestController
@RequestMapping("/api/v1/builds")
@RequiredArgsConstructor
public class BuildController {

    private final ObjectMapper objectMapper;
    private final BuildService buildService;

    @PostMapping("")
    public ResponseEntity<?> createBuild(@RequestParam("projectName") String projectName,
                                         @RequestParam("repository") String repository,
                                         @RequestParam("pipelineName") String pipelineName,
                                         @RequestParam("branch") String branch,
                                         @RequestParam("buildNumber") int buildNumber,
                                         @RequestParam("result") String result,
                                         @RequestParam("duration") long duration,
                                         @RequestParam("startAt") long startAt,
                                         @RequestPart("bom") MultipartFile file) {
        try {
            SbomDto sbom = objectMapper.readValue(file.getInputStream(), SbomDto.class);
            log.info("SBOM: {}", sbom);
            LocalDateTime startAtDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(startAt), TimeZone.getDefault().toZoneId());
            buildService.createBuild(projectName, pipelineName, repository, branch, buildNumber, result, duration, startAtDate, sbom);
            return ResponseEntity.ok(
                    ApiResponse.builder()
                            .success(true)
                            .message("SBOM uploaded successfully")
                            .build()
            );
        } catch (IOException e) {
            log.error("Error while analyzing SBOM file", e);
            throw new BadRequestException("Error while analyzing SBOM file");
        }
    }

    @GetMapping("/compare")
    public ResponseEntity<?> compareBuilds(@RequestParam("from") long buildId1,
                                          @RequestParam("to") long buildId2) {
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Compare builds successfully")
                        .success(true)
                        .data(buildService.compareBuilds(buildId1, buildId2))
                        .build()
        );
    }

    @GetMapping("/{buildId}/components")
    public ResponseEntity<?> getComponentsOfBuild(@PathVariable("buildId") long buildId) {
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Components retrieved successfully")
                        .success(true)
                        .data(buildService.getDetailComponentsByBuildId(buildId))
                        .build()
        );
    }

    @GetMapping("/{buildId}/dependencies")
    public ResponseEntity<?> getDependenciesOfBuild(@PathVariable("buildId") long buildId) {
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Dependencies retrieved successfully")
                        .success(true)
                        .data(buildService.getDependenciesByBuildId(buildId))
                        .build()
        );
    }
}
