package com.vinhdd.sbom.api.service;

import com.vinhdd.sbom.api.dto.out.DependencyDtoOut;
import com.vinhdd.sbom.api.dto.sbomfile.SbomDto;
import com.vinhdd.sbom.api.model.Sbom;

import java.util.List;

public interface SbomService {
    Sbom save(SbomDto dto);
    List<DependencyDtoOut> getDependenciesOfSbom(Long sbomId);
}
