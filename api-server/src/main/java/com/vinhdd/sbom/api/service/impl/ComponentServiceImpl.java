package com.vinhdd.sbom.api.service.impl;

import com.vinhdd.sbom.api.dto.out.ComponentDtoOut;
import com.vinhdd.sbom.api.dto.out.DependencyDtoOut;
import com.vinhdd.sbom.api.dto.out.RecursiveDependencyDto;
import com.vinhdd.sbom.api.dto.sbomfile.ComponentDto;
import com.vinhdd.sbom.api.model.Component;
import com.vinhdd.sbom.api.repository.ComponentRepository;
import com.vinhdd.sbom.api.repository.LicenseRepository;
import com.vinhdd.sbom.api.service.ComponentService;
import com.vinhdd.sbom.api.util.helper.QueryResultMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ComponentServiceImpl implements ComponentService {
    private final ComponentRepository componentRepository;
    private final LicenseRepository licenseRepository;
    private final QueryResultMapper queryResultMapper;

    @Override
    public List<Component> fromDtoList(List<ComponentDto> dtoList) {
        return List.of();
    }

    @Override
    public Component fromDto(ComponentDto dto) {
        Component component = componentRepository.findByPurl(dto.getPurl());
        if (component != null) {
            return component;
        }
        Component entity = new Component();
        entity.setPublisher(dto.getPublisher());
        entity.setGroupName(dto.getGroup());
        entity.setName(dto.getName());
        entity.setVersion(dto.getVersion());
        entity.setDescription(dto.getDescription());
        entity.setScope(dto.getScope());
        entity.setHashes(dto.getHashes());
        entity.setLicenses(dto.getLicenses().stream().map(
                licenseWrapper -> licenseRepository.findByLicenseId(licenseWrapper.getLicense().getId())).collect(Collectors.toSet()));
        entity.setPurl(dto.getPurl());
        entity.setExternalReferences(dto.getExternalReferences());
        entity.setType(dto.getType());
        entity.setBomRef(dto.getBomRef());
        entity.setEvidence(dto.getEvidence());
        entity.setProperties(dto.getProperties());
        return entity;
    }

    @Override
    @Transactional
    public ComponentDtoOut getByPurl(String purl) {
        ComponentDtoOut component =  ComponentDtoOut.from(componentRepository.findByPurl(purl));
        component.setDependencies(getRecursiveDependencies(purl));
        return component;
    }

    @Override
    public RecursiveDependencyDto getRecursiveDependencies(String purl) {
        RecursiveDependencyDto dependencyDto = new RecursiveDependencyDto();
        dependencyDto.setRef(purl);
        dependencyDto.setDependsOn(componentRepository.getAllDependenciesByPurl(purl).stream().map(
                this::getRecursiveDependencies
        ).collect(Collectors.toSet()));
        return dependencyDto;
    }

    @Override
    public DependencyDtoOut getDependenciesOfComponent(String purl) {
        return queryResultMapper.mapResult(componentRepository.getDirectDependenciesOfComponent(purl), DependencyDtoOut.class);
    }
}
