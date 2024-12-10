package com.vinhdd.sbom.api.service;

import com.vinhdd.sbom.api.dto.out.ComponentDtoOut;
import com.vinhdd.sbom.api.dto.out.DependencyDtoOut;
import com.vinhdd.sbom.api.dto.out.RecursiveDependencyDto;
import com.vinhdd.sbom.api.dto.sbomfile.ComponentDto;
import com.vinhdd.sbom.api.dto.sbomfile.DependencyDto;
import com.vinhdd.sbom.api.model.Component;

import java.util.List;

public interface ComponentService {
    List<Component> fromDtoList(List<ComponentDto> dtoList);
    Component fromDto(ComponentDto dto);
    ComponentDtoOut getByPurl(String purl);
    RecursiveDependencyDto getRecursiveDependencies(String purl);
    DependencyDtoOut getDependenciesOfComponent(String purl);
}
