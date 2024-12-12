package com.vinhdd.sbom.api.service.impl;

import com.vinhdd.sbom.api.dto.out.DependencyDtoOut;
import com.vinhdd.sbom.api.dto.sbomfile.ComponentDto;
import com.vinhdd.sbom.api.model.Component;
import com.vinhdd.sbom.api.repository.ComponentRepository;
import com.vinhdd.sbom.api.repository.LicenseRepository;
import com.vinhdd.sbom.api.service.ComponentService;
import com.vinhdd.sbom.api.util.helper.QueryResultMapper;
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
        Component entity = dto.toEntity();
        entity.setLicenses(dto.getLicenses().stream().map(licenseWrapper ->
                licenseRepository.findByLicenseId(licenseWrapper.getLicense().getId())).collect(Collectors.toSet()));
        return entity;
    }

    @Override
    public DependencyDtoOut getDependenciesOfComponent(String purl) {
        return queryResultMapper.mapResult(componentRepository.getDirectDependenciesOfComponent(purl), DependencyDtoOut.class);
    }
}
