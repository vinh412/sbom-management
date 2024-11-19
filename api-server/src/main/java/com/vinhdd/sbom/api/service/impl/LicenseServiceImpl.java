package com.vinhdd.sbom.api.service.impl;

import com.vinhdd.sbom.api.exception.CommonException;
import com.vinhdd.sbom.api.model.License;
import com.vinhdd.sbom.api.repository.LicenseRepository;
import com.vinhdd.sbom.api.service.LicenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LicenseServiceImpl implements LicenseService {
    private final LicenseRepository licenseRepository;

    @Override
    public License findByLicenseId(String licenseId) {
        License license = licenseRepository.findByLicenseId(licenseId);
        if (license == null) {
            throw new CommonException("License id not found", HttpStatus.NOT_FOUND);
        }
        return license;
    }

    @Override
    public Page<License> getLicensesBySearchString(String searchString, Pageable pageable) {
        return licenseRepository.searchByKeyword(searchString, pageable);
    }
}
