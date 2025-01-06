package com.vinhdd.sbom.api.dto.in;

import lombok.Data;

import java.util.List;

@Data
public class AdminUpdateUserDtoIn {
    private Integer status;
    private List<String> roles;
}
