package com.vinhdd.sbom.api.dto.sbomfile;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MetadataDto {
    private LocalDateTime timestamp;
    private ComponentDto component;
}
