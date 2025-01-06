package com.vinhdd.sbom.api.dto.in;

import lombok.Data;

@Data
public class UpdateUserDto {
    private String username;
    private String fullname;
    private String email;
    private String phoneNumber;
    private String description;
}
