package com.vinhdd.sbom.api.dto;

import com.vinhdd.sbom.api.model.Build;
import com.vinhdd.sbom.api.util.helper.Col;
import lombok.Data;

import java.time.format.DateTimeFormatter;

@Data
public class BuildDto {
    @Col("id")
    private Long id;
    @Col("name")
    private String name;
    @Col("created_at")
    private String createdAt;
    @Col("sbom_id")
    private Long sbomId;

    public static BuildDto fromEntity(Build build) {
        BuildDto dto = new BuildDto();
        dto.setId(build.getId());
        dto.setName(build.getName());
        dto.setCreatedAt(build.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        dto.setSbomId(build.getSbom().getId());
        return dto;
    }
}
