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

@Slf4j
@RestController
@RequestMapping("/api/v1/builds")
@RequiredArgsConstructor
public class BuildController {

    private final ObjectMapper objectMapper;
    private final BuildService buildService;

    @PostMapping("")
    public ResponseEntity<?> createBuild(@RequestPart("projectName") String projectName,
                                         @RequestPart("pipelineName") String pipelineName,
                                         @RequestPart("buildNumber") String buildNumber,
                                         @RequestPart("bom") MultipartFile file) {
        try {
            SbomDto sbom = objectMapper.readValue(file.getInputStream(), SbomDto.class);
            log.info("SBOM: {}", sbom);
            buildService.createBuild(projectName, pipelineName, buildNumber, sbom);
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
}
