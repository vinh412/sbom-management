package com.vinhdd.sbom.api.dto;

import com.vinhdd.sbom.api.model.Build;
import lombok.Data;

import java.time.format.DateTimeFormatter;

@Data
public class BuildDto {
    private Long id;
    private int number;
    private String createdAt;
    private Long sbomId;
    private String result;
    private Long duration;
    private String startAt;
    public static BuildDto fromEntity(Build build) {
        BuildDto dto = new BuildDto();
        dto.setId(build.getId());
        dto.setNumber(build.getNumber());
        dto.setCreatedAt(build.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        dto.setSbomId(build.getSbom().getId());
        dto.setResult(build.getResult());
        dto.setDuration(build.getDuration());
        dto.setStartAt(build.getStartAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return dto;
    }
}
