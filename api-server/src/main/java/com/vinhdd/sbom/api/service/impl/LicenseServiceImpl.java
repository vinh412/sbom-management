package com.vinhdd.sbom.api.service.impl;

import com.vinhdd.sbom.api.dto.in.PageRequestDtoIn;
import com.vinhdd.sbom.api.exception.CommonException;
import com.vinhdd.sbom.api.model.License;
import com.vinhdd.sbom.api.repository.LicenseRepository;
import com.vinhdd.sbom.api.service.LicenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public Page<License> getLicensesBySearchString(PageRequestDtoIn pageRequestDtoIn) {
        Sort sort = Sort.by(pageRequestDtoIn.getSortBy());
        if(pageRequestDtoIn.getOrder().equals("desc")) {
            sort = sort.descending();
        }
        Pageable pageable = PageRequest.of(pageRequestDtoIn.getPage() - 1, pageRequestDtoIn.getSize(), sort);
        return licenseRepository.searchByKeyword(pageRequestDtoIn.getSearch(), pageable);
    }
}
