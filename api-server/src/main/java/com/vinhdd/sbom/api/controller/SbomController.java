package com.vinhdd.sbom.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vinhdd.sbom.api.dto.out.ApiResponse;
import com.vinhdd.sbom.api.dto.sbomfile.SbomDto;
import com.vinhdd.sbom.api.repository.ComponentRepository;
import com.vinhdd.sbom.api.service.ComponentService;
import com.vinhdd.sbom.api.service.SbomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/v1/sbom")
@RequiredArgsConstructor
public class SbomController {
    private final ObjectMapper mapper;
    private final SbomService sbomService;
    private final ComponentService componentService;

    @PostMapping("")
    public void analyzeSbom(@RequestPart("bom") MultipartFile file) {
        // Analyze SBOM file
        try {
            SbomDto sbom = mapper.readValue(file.getInputStream(), SbomDto.class);
            log.info("SBOM: {}", sbom);
            sbomService.save(sbom);
        } catch (IOException e) {
            log.error("Error while analyzing SBOM file", e);
        }
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<?>> getComponent(@Param("purl") String purl) {
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .success(true)
                        .message("Get component success")
                        .data(componentService.getByPurl(purl))
                        .build()
        );

    }

    @GetMapping("/dependencies")
    public ResponseEntity<ApiResponse<?>> getDependencies(@Param("sbomId") Long sbomId) {
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .success(true)
                        .message("Get dependencies success")
                        .data(sbomService.getDependenciesOfSbom(sbomId))
                        .build()
        );
    }
}
