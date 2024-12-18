package com.vinhdd.sbom.api.core;

import com.vinhdd.sbom.api.model.Component;
import com.vinhdd.sbom.api.repository.ComponentRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MavenPackageService implements PackageService {
    private final ComponentRepository componentRepository;
    private final RestTemplate restTemplate;

    @Override
    public void updateComponent(Component component) {
        String groupName = component.getGroupName();
        String name = component.getName();
        ComponentResponse response = restTemplate.getForObject("https://central.sonatype.com/api/internal/browse/component/versions?sortField=normalizedVersion&sortDirection=desc&page=0&size=1&filter=namespace:{groupName},name:{name}", ComponentResponse.class, groupName, name);
        if (response != null && !response.getComponents().isEmpty()) {
            String latestVersion = response.getComponents().get(0).getVersion();
            if(latestVersion != null && !latestVersion.isEmpty()){
                component.setLatestVersion(latestVersion);
                componentRepository.save(component);

            }
        }
    }
}

@Data
class ComponentResponse {
    private List<ComponentVersion> components;
}

@Data
class ComponentVersion {
    private String version;
}
