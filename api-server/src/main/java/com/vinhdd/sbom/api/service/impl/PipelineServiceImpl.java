package com.vinhdd.sbom.api.service.impl;

import com.vinhdd.sbom.api.dto.in.CreatePipelineDtoIn;
import com.vinhdd.sbom.api.dto.out.PipelineDTO;
import com.vinhdd.sbom.api.model.Pipeline;
import com.vinhdd.sbom.api.model.Project;
import com.vinhdd.sbom.api.repository.PipelineRepository;
import com.vinhdd.sbom.api.repository.ProjectRepository;
import com.vinhdd.sbom.api.service.PipelineService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PipelineServiceImpl implements PipelineService {
    private final PipelineRepository pipelineRepository;
    private final ProjectRepository projectRepository;

    @Override
    public List<PipelineDTO> findAllByProjectId(String projectId){
        return pipelineRepository.findAllByProjectId(projectId)
                .stream()
                .map(pipeline -> PipelineDTO.builder()
                        .id(pipeline.getId())
                        .name(pipeline.getName())
                        .createdAt(pipeline.getCreatedAt())
                        .updatedAt(pipeline.getUpdatedAt())
                        .build())
                .toList();
    }

    @Override
    public Pipeline getPipelineById(int id) {
        return null;
    }

    @Override
    public Pipeline createPipeline(CreatePipelineDtoIn pipelineDtoIn) {
        Pipeline pipeline = new Pipeline();
        pipeline.setName(pipelineDtoIn.getName());
        pipeline.setProjectId(pipelineDtoIn.getProjectId());
        return pipelineRepository.save(pipeline);
    }

    @Override
    public Pipeline updatePipeline(Pipeline pipeline) {
        return null;
    }

    @Override
    public void deletePipeline(int id) {

    }
}
