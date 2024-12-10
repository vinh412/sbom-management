package com.vinhdd.sbom.api.dto.sbomfile;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class SbomDto {
    private String bomFormat;
    private String specVersion;
    private String serialNumber;
    private int version;
    private MetadataDto metadata;
    private List<ComponentDto> components;
    private List<DependencyDto> dependencies;

//    public static SbomDto from(Sbom entity) {
//        return SbomDto.builder()
//                .bomFormat(entity.getBomFormat())
//                .specVersion(entity.getSpecVersion())
//                .serialNumber(entity.getSerialNumber())
//                .version(entity.getVersion())
//                .metadata(entity.getMetadata())
//                .components(entity.getComponents().stream().map(ComponentDto::from).collect(Collectors.toList()))
//                .dependencies(entity.getDependencies().stream().map(DependencyDto::from).collect(Collectors.toList()))
//                .build();
//    }
}

