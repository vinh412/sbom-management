package com.vinhdd.sbom.api.controller;

import com.vinhdd.sbom.api.dto.UserDto;
import com.vinhdd.sbom.api.dto.in.ChangePasswordDtoIn;
import com.vinhdd.sbom.api.dto.in.UpdateUserDto;
import com.vinhdd.sbom.api.dto.out.ApiResponse;
import com.vinhdd.sbom.api.service.AuthService;
import com.vinhdd.sbom.api.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/me")
public class MeController {
    private final UserService userService;
    private final AuthService authService;

    @PatchMapping("/change-password")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordDtoIn changePasswordDtoIn) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        userService.changePassword(username, changePasswordDtoIn);
        return ResponseEntity.ok().body(
                ApiResponse.builder()
                        .success(true)
                        .message("Change password success!")
                        .build()
        );
    }

    @PatchMapping("")
    public ResponseEntity<?> update(@RequestBody UpdateUserDto dto) {
        String userId = authService.getCurrentUser().getId();
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .success(true)
                        .message("Update account successfully")
                        .data(userService.update(userId, dto))
                        .build()
        );
    }

    @GetMapping("")
    public ResponseEntity<?> getProfile() {
        UserDto user = userService.getByUsername(authService.getCurrentUser().getUsername());
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .success(true)
                        .message("Get me successfully")
                        .data(user)
                        .build()
        );
    }
}
