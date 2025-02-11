package com.vinhdd.sbom.api.service.impl;

import com.vinhdd.sbom.api.core.MavenPackageService;
import com.vinhdd.sbom.api.core.NpmPackageService;
import com.vinhdd.sbom.api.dto.out.DependencyDtoOut;
import com.vinhdd.sbom.api.dto.queryout.ComponentQueryOut;
import com.vinhdd.sbom.api.dto.sbomfile.ComponentDto;
import com.vinhdd.sbom.api.model.Component;
import com.vinhdd.sbom.api.repository.ComponentRepository;
import com.vinhdd.sbom.api.repository.LicenseRepository;
import com.vinhdd.sbom.api.service.AuthService;
import com.vinhdd.sbom.api.service.ComponentService;
import com.vinhdd.sbom.api.util.helper.QueryResultMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ComponentServiceImpl implements ComponentService {
    private final ComponentRepository componentRepository;
    private final LicenseRepository licenseRepository;
    private final QueryResultMapper queryResultMapper;
    private final NpmPackageService npmPackageService;
    private final MavenPackageService mavenPackageService;
    private final AuthService authService;

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

    @Override
    public List<ComponentQueryOut> getComponents() {
        return componentRepository.getComponents(authService.getCurrentUser().getId())
                .stream()
                .map(component -> queryResultMapper.mapResult(component, ComponentQueryOut.class))
                .collect(Collectors.toList());
    }

    @Scheduled()
    public void updateComponent() {
        // Update component
        log.info("Updating component...");
        List<Component> components = componentRepository.findAll();
        components.forEach(component -> {
            if (component.getPurl().startsWith("pkg:maven")) {
                mavenPackageService.updateComponent(component);
            }
            else if (component.getPurl().startsWith("pkg:npm")) {
                npmPackageService.updateComponent(component);
            }
        });
        log.info("Updated {} components", components.size());
    }
}
