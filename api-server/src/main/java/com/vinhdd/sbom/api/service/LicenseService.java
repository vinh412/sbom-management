package com.vinhdd.sbom.api.service;

import com.vinhdd.sbom.api.model.License;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LicenseService {
    License findByLicenseId(String licenseId);
    Page<License> getLicensesBySearchString(String searchString, Pageable pageable);
}
