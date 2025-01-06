package com.vinhdd.sbom.api.dto.in;

import lombok.Data;

import java.util.List;

@Data
public class UpdateMemberDtoIn {
    private Boolean isAdmin;
    private List<String> pipelineIds;
}