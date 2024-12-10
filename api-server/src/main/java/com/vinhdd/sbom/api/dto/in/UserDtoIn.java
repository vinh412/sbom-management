package com.vinhdd.sbom.api.dto.in;

import lombok.Data;

import java.util.Set;

@Data
public class UserDtoIn {
    private String username;
    private String fullname;
    private String email;
    private String phoneNumber;
    private String description;
    private Set<String> roles;
}
