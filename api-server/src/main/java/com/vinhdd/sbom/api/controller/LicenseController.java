package com.vinhdd.sbom.api.controller;

import com.vinhdd.sbom.api.dto.out.ApiResponse;
import com.vinhdd.sbom.api.service.LicenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/licenses")
@RequiredArgsConstructor
public class LicenseController {
    private final LicenseService licenseService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> getLicense(@PathVariable String id) {
        return ResponseEntity.ok().body(
                ApiResponse.builder()
                        .success(true)
                        .message("Get license by id successful")
                        .data(licenseService.findByLicenseId(id))
                        .build()
        );
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<?>> getAllLicenses(@RequestParam int page,
                                                         @RequestParam int size,
                                                         @RequestParam String search,
                                                         @RequestParam String sortBy,
                                                         @RequestParam String order) {
        Sort sort = Sort.by(sortBy);
        if(order.equals("desc")) {
            sort = sort.descending();
        }
        Pageable pageable = PageRequest.of(page-1, size, sort);
        return ResponseEntity.ok().body(
                ApiResponse.builder()
                        .success(true)
                        .message("Get license list successful")
                        .data(new PagedModel<>(licenseService.getLicensesBySearchString(search, pageable)))
                        .build()
        );
    }
}