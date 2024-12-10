package com.vinhdd.sbom.api.service.impl;

import com.vinhdd.sbom.api.dto.out.DependencyDtoOut;
import com.vinhdd.sbom.api.dto.sbomfile.SbomDto;
import com.vinhdd.sbom.api.model.Component;
import com.vinhdd.sbom.api.model.Sbom;
import com.vinhdd.sbom.api.repository.ComponentRepository;
import com.vinhdd.sbom.api.repository.SbomRepository;
import com.vinhdd.sbom.api.service.ComponentService;
import com.vinhdd.sbom.api.service.SbomService;
import com.vinhdd.sbom.api.util.helper.QueryResultMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SbomServiceImpl implements SbomService {
    private final ComponentService componentService;
    private final ComponentRepository componentRepository;
    private final SbomRepository sbomRepository;
    private final QueryResultMapper queryResultMapper;

    private Sbom fromDto(SbomDto dto) {
        return Sbom.builder()
                .bomFormat(dto.getBomFormat())
                .specVersion(dto.getSpecVersion())
                .serialNumber(dto.getSerialNumber())
                .version(dto.getVersion())
                .component(componentService.fromDto(dto.getMetadata().getComponent()))
                .components(dto.getComponents().stream().map(componentService::fromDto).collect(Collectors.toSet()))
                .build();
    }

    @Override
    @Transactional
    public Sbom save(SbomDto dto) {
        Sbom sbom = new Sbom();
        sbom.setBomFormat(dto.getBomFormat());
        sbom.setSpecVersion(dto.getSpecVersion());
        sbom.setSerialNumber(dto.getSerialNumber());
        sbom.setVersion(dto.getVersion());
        sbom.setComponent(componentService.fromDto(dto.getMetadata().getComponent()));
        dto.getComponents().forEach(componentDto -> {
            Component c = componentRepository.findByPurl(componentDto.getPurl());
            if(c==null){
                c = componentService.fromDto(componentDto);
            }
            sbom.addComponent(c);
        });
        sbom.setCreatedAt(dto.getMetadata().getTimestamp());

        Sbom entity = sbomRepository.save(sbom);

        dto.getDependencies().forEach(
                dependencyDto -> {
                    Component component = componentRepository.findByBomRef(dependencyDto.getRef());
                    component.getDependencies().clear();
                    component.getDependencies().addAll(dependencyDto.getDependsOn().stream().map(componentRepository::findByBomRef).collect(Collectors.toSet()));
                    componentRepository.save(component);
                }
        );

        return entity;
    }

    @Override
    public List<DependencyDtoOut> getDependenciesOfSbom(Long sbomId) {
        Sbom sbom = sbomRepository.findById(sbomId).orElseThrow();
        return getAllDependenciesOfComponent(sbom.getComponent().getPurl(), new HashSet<>());
    }

    public List<DependencyDtoOut> getAllDependenciesOfComponent(String purl, Set<String> visited){
        List<DependencyDtoOut> result = new ArrayList<>();
        if(visited.contains(purl)){
            return result;
        }
        DependencyDtoOut component = getDirectDependenciesOfComponent(purl);
        visited.add(purl);
        result.add(component);
        if(!component.getDependsOn().isEmpty()){
            component.getDependsOn().forEach(
                    dependency -> {
                        result.addAll(getAllDependenciesOfComponent(dependency, visited));
                    }
            );
        }
        return result;
    }

    private DependencyDtoOut getDirectDependenciesOfComponent(String purl){
        return queryResultMapper.mapResult(componentRepository.getDirectDependenciesOfComponent(purl), DependencyDtoOut.class);
    }
}
