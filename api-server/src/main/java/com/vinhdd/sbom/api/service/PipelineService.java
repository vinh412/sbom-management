package com.vinhdd.sbom.api.service;

import com.vinhdd.sbom.api.dto.in.CreatePipelineDtoIn;
import com.vinhdd.sbom.api.dto.out.PipelineDTO;
import com.vinhdd.sbom.api.model.Pipeline;

import java.util.List;

public interface PipelineService {
    List<PipelineDTO> findAllByProjectId(String projectId);
    Pipeline getPipelineById(int id);
    Pipeline createPipeline(CreatePipelineDtoIn pipelineDtoIn);
    Pipeline updatePipeline(Pipeline pipeline);
    void deletePipeline(int id);
}
