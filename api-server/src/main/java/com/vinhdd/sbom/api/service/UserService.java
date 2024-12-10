package com.vinhdd.sbom.api.service;

import com.vinhdd.sbom.api.dto.UserDto;
import com.vinhdd.sbom.api.dto.in.PageRequestDtoIn;
import com.vinhdd.sbom.api.dto.in.UserDtoIn;
import org.springframework.data.domain.Page;

public interface UserService {
    Page<UserDto> getAllBySearchString(PageRequestDtoIn pageRequestDtoIn);
    UserDto getByUsername(String username);
    UserDto create(UserDtoIn userDtoIn);
    UserDto update(UserDtoIn userDtoIn);
    void delete(String username);
}
