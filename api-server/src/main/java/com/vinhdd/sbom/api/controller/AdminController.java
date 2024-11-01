package com.vinhdd.sbom.api.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {
    @PreAuthorize("hasAuthority('BOM_UPLOAD')")
    @GetMapping("")
    public String admin(){
        return "admin";
    }
}
