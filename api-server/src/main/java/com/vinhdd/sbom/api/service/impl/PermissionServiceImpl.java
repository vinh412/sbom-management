package com.vinhdd.sbom.api.service.impl;

import com.vinhdd.sbom.api.dto.PermissionDto;
import com.vinhdd.sbom.api.dto.in.PageRequestDtoIn;
import com.vinhdd.sbom.api.model.Permission;
import com.vinhdd.sbom.api.repository.PermissionRepository;
import com.vinhdd.sbom.api.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {
    private final PermissionRepository permissionRepository;

    @Override
    public Page<PermissionDto> getAll(PageRequestDtoIn pageRequestDtoIn) {
        Sort sort = Sort.by(pageRequestDtoIn.getSortBy());
        if (pageRequestDtoIn.getOrder().equals("desc")) {
            sort = sort.descending();
        }
        Pageable pageable = PageRequest.of(pageRequestDtoIn.getPage() - 1, pageRequestDtoIn.getSize(), sort);
        return permissionRepository.findAllByIdContaining(pageRequestDtoIn.getSearch(), pageable)
                .map(PermissionDto::from);
    }
}
