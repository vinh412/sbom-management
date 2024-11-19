package com.vinhdd.sbom.api.controller;

import com.vinhdd.sbom.api.dto.in.CreatePipelineDtoIn;
import com.vinhdd.sbom.api.dto.out.ApiResponse;
import com.vinhdd.sbom.api.model.Pipeline;
import com.vinhdd.sbom.api.service.PipelineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/projects/{projectId}/pipelines")
public class PipelineController {
    private final PipelineService pipelineService;

    @PostMapping("")
    public ResponseEntity<?> createPipeline(@PathVariable("projectId") String projectId,
                                            @RequestBody CreatePipelineDtoIn pipeline){
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.builder()
                        .success(true)
                        .message("Pipeline created successfully")
                        .data(pipelineService.createPipeline(pipeline))
                        .build()
        );
    }
}
