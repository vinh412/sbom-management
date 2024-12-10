package com.vinhdd.sbom.api.dto;

import com.vinhdd.sbom.api.model.Permission;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PermissionDto {
    private String permission;
    private String description;

    public static PermissionDto from(Permission permission) {
        return PermissionDto.builder()
            .permission(permission.getId())
            .description(permission.getDescription())
            .build();
    }
}
