package com.vinhdd.sbom.api.dto.queryout;

import com.vinhdd.sbom.api.util.helper.Col;
import lombok.Data;

@Data
public class PipelineQueryOutDto {
    @Col("id")
    private String id;
    @Col("name")
    private String name;
    @Col("project_id")
    private String projectId;
    @Col("description")
    private String description;
    @Col("branch")
    private String branch;
}
