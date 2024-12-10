package com.vinhdd.sbom.api.dto;

import com.vinhdd.sbom.api.model.User;
import lombok.Builder;
import lombok.Data;

import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
public class UserDto {
    private String username;
    private String fullname;
    private String email;
    private String phoneNumber;
    private String description;
    private String createdAt;
    private String updatedAt;
    private Set<RoleDto> roles;

    public static UserDto from(User user) {
        return UserDto.builder()
                .username(user.getUsername())
                .fullname(user.getFullname())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .description(user.getDescription())
                .createdAt(user.getCreatedAt().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")))
                .updatedAt(user.getUpdatedAt().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")))
                .roles(user.getRoles().stream()
                        .map(RoleDto::from)
                        .collect(Collectors.toSet()))
                .build();
    }
}
