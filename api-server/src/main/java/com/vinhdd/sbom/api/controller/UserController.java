package com.vinhdd.sbom.api.controller;

import com.vinhdd.sbom.api.dto.in.PageRequestDtoIn;
import com.vinhdd.sbom.api.dto.in.UserDtoIn;
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
    @PreAuthorize("hasAuthority('VIEW_USERS')")
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
    @PreAuthorize("hasAuthority('VIEW_USERS')")
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
    @PreAuthorize("hasAuthority('CREATE_USER')")
    public ResponseEntity<?> createUser(@RequestBody UserDtoIn userDtoIn) {
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .success(true)
                        .message("Create account successfully")
                        .data(userService.create(userDtoIn))
                        .build()
        );
    }

    @PutMapping("")
    public ResponseEntity<?> updateUser(@RequestBody UserDtoIn userDtoIn) {
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .success(true)
                        .message("Update account successfully")
                        .data(userService.update(userDtoIn))
                        .build()
        );
    }

    @DeleteMapping("/{username}")
    @PreAuthorize("hasAuthority('DELETE_USER')")
    public ResponseEntity<?> deleteUser(@PathVariable String username) {
        userService.delete(username);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .success(true)
                        .message("Delete account successfully")
                        .build()
        );
    }
}