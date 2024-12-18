package com.vinhdd.sbom.api.core;

import com.vinhdd.sbom.api.dto.restTemplate.PackageInfoDto;
import com.vinhdd.sbom.api.model.Component;
import com.vinhdd.sbom.api.model.License;
import com.vinhdd.sbom.api.repository.ComponentRepository;
import com.vinhdd.sbom.api.repository.LicenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Set;

@RequiredArgsConstructor
@Service
public class NpmPackageService implements PackageService {
    private final RestTemplate restTemplate;
    private final ComponentRepository componentRepository;
    private final LicenseRepository licenseRepository;

    @Override
    public void updateComponent(Component component) throws RestClientException {
        String packageName = component.getName();
        if(!component.getGroupName().isEmpty()){
            packageName = component.getGroupName() + "/" + packageName;
        }
        String url = "https://registry.npmjs.org/" + packageName + "/latest";
        PackageInfoDto packageInfo = restTemplate.getForObject(url, PackageInfoDto.class);
        if (packageInfo != null) {
            License license = licenseRepository.findByLicenseId(packageInfo.getLicense());
            component.setLatestVersion(packageInfo.getVersion());
            component.setLicenses(Set.of(license));
            component.setDescription(packageInfo.getDescription());
            componentRepository.save(component);
        }
    }
}
