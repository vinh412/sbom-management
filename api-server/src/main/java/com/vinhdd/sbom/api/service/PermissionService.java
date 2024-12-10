package com.vinhdd.sbom.api.service;

import com.vinhdd.sbom.api.dto.PermissionDto;
import com.vinhdd.sbom.api.dto.in.PageRequestDtoIn;
import org.springframework.data.domain.Page;


public interface PermissionService {
    Page<PermissionDto> getAll(PageRequestDtoIn pageRequestDtoIn);
}
