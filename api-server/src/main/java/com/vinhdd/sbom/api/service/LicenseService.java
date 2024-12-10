package com.vinhdd.sbom.api.service;

import com.vinhdd.sbom.api.dto.in.PageRequestDtoIn;
import com.vinhdd.sbom.api.dto.sbomfile.LicenseDto;
import com.vinhdd.sbom.api.model.License;
import org.springframework.data.domain.Page;

import java.util.List;

public interface LicenseService {
    License findByLicenseId(String licenseId);
    Page<License> getLicensesBySearchString(PageRequestDtoIn pageRequestDtoIn);
}
