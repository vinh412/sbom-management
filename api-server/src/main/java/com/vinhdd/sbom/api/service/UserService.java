package com.vinhdd.sbom.api.service;

import com.vinhdd.sbom.api.dto.UserDto;
import com.vinhdd.sbom.api.dto.in.*;
import org.springframework.data.domain.Page;

public interface UserService {
    Page<UserDto> getAllBySearchString(PageRequestDtoIn pageRequestDtoIn);
    UserDto getByUsername(String username);
    void create(CreateUserDtoIn createUserDtoIn);
    UserDto adminUpdate(String userId, AdminUpdateUserDtoIn adminUpdateUserDtoIn);
    UserDto update(String userId, UpdateUserDto userDtoIn);
    void delete(String username);
    void changePassword(String userId, ChangePasswordDtoIn changePasswordDtoIn);
    void changePassword(AdminChangePasswordDtoIn changePasswordDtoIn);
}
