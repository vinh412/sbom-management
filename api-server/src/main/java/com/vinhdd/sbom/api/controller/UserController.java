package com.vinhdd.sbom.api.controller;

import com.vinhdd.sbom.api.dto.in.*;
import com.vinhdd.sbom.api.dto.out.ApiResponse;
import com.vinhdd.sbom.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("")
    @PreAuthorize("hasAnyAuthority('SYS_ADMIN', 'VIEW_USERS')")
    public ResponseEntity<?> getAllUsers(PageRequestDtoIn pageRequestDtoIn) {
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .success(true)
                        .message("Get all users successfully")
                        .data(new PagedModel<>(userService.getAllBySearchString(pageRequestDtoIn)))
                        .build()
        );
    }

    @GetMapping("/{username}")
    @PreAuthorize("hasAnyAuthority('SYS_ADMIN', 'VIEW_USERS')")
    public ResponseEntity<?> getUser(@PathVariable String username) {
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .success(true)
                        .message("Get user by username successfully")
                        .data(userService.getByUsername(username))
                        .build()
        );
    }

    @PostMapping("")
    @PreAuthorize("hasAnyAuthority('SYS_ADMIN', 'CREATE_USER')")
    public ResponseEntity<?> createUser(@RequestBody CreateUserDtoIn createUserDtoIn) {
        userService.create(createUserDtoIn);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .success(true)
                        .message("Create account successfully")
                        .build()
        );
    }

    @PatchMapping("/{userId}")
    @PreAuthorize("hasAnyAuthority('SYS_ADMIN', 'UPDATE_USER')")
    public ResponseEntity<?> updateUser(@PathVariable String userId, @RequestBody AdminUpdateUserDtoIn dto) {
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .success(true)
                        .message("Update account successfully")
                        .data(userService.adminUpdate(userId, dto))
                        .build()
        );
    }

    @DeleteMapping("/{username}")
    @PreAuthorize("hasAnyAuthority('SYS_ADMIN', 'DELETE_USER')")
    public ResponseEntity<?> deleteUser(@PathVariable String username) {
        userService.delete(username);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .success(true)
                        .message("Delete account successfully")
                        .build()
        );
    }

    @PatchMapping("/change-password")
    @PreAuthorize("hasAnyAuthority('SYS_ADMIN', 'CHANGE_USER_PASSWORD')")
    public ResponseEntity<?> changePassword(@RequestBody AdminChangePasswordDtoIn changePasswordDtoIn) {
        userService.changePassword(changePasswordDtoIn);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .success(true)
                        .message("Change password successfully")
                        .build()
        );
    }
}