package com.vinhdd.sbom.api.service.impl;

import com.vinhdd.sbom.api.dto.UserDto;
import com.vinhdd.sbom.api.dto.in.*;
import com.vinhdd.sbom.api.exception.BadRequestException;
import com.vinhdd.sbom.api.exception.NotFoundException;
import com.vinhdd.sbom.api.model.User;
import com.vinhdd.sbom.api.repository.RoleRepository;
import com.vinhdd.sbom.api.repository.UserRepository;
import com.vinhdd.sbom.api.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Page<UserDto> getAllBySearchString(PageRequestDtoIn pageRequestDtoIn) {
        Sort sort = Sort.by(pageRequestDtoIn.getSortBy());
        if (pageRequestDtoIn.getOrder().equals("acs")) {
            sort = sort.descending();
        }
        Pageable pageable = PageRequest.of(pageRequestDtoIn.getPage() - 1, pageRequestDtoIn.getSize(), sort);
        return userRepository.findAllByUsernameContaining(pageRequestDtoIn.getSearch(), pageable).map(UserDto::from);
    }

    @Override
    public UserDto getByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new NotFoundException("User not found: " + username);
        }
        return UserDto.from(user);
    }

    @Override
    public void create(CreateUserDtoIn createUserDtoIn) {
        User user = new User();
        user.setUsername(createUserDtoIn.getUsername());
        user.setPassword(passwordEncoder.encode(createUserDtoIn.getPassword()));
        user.setStatus(1);
        user.setRoles(new HashSet<>(List.of(roleRepository.findByName("USER")
                .orElseThrow(() -> new NotFoundException("Role not found: USER")))));
        userRepository.save(user);
    }

    @Override
    public UserDto adminUpdate(String userId, AdminUpdateUserDtoIn adminUpdateUserDtoIn) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found: " + userId));
        if(adminUpdateUserDtoIn.getStatus() != null){
            user.setStatus(adminUpdateUserDtoIn.getStatus());
        }
        if (adminUpdateUserDtoIn.getRoles() != null) {
            user.setRoles(new HashSet<>(adminUpdateUserDtoIn.getRoles().stream().map(roleName ->
                    roleRepository.findByName(roleName)
                            .orElseThrow(() -> new BadRequestException("Role not found: " + roleName))
            ).toList()));
        }
        User savedUser = userRepository.save(user);
        return UserDto.from(savedUser);
    }

    @Override
    @Transactional
    public UserDto update(String userId, UpdateUserDto userDtoIn) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found: " + userId));
        user.setFullname(userDtoIn.getFullname());
        user.setEmail(userDtoIn.getEmail());
        user.setPhoneNumber(userDtoIn.getPhoneNumber());
        user.setDescription(userDtoIn.getDescription());
        user.setUsername(userDtoIn.getUsername());
        userRepository.save(user);
        return UserDto.from(user);
    }

    @Override
    public void delete(String username) {
        userRepository.deleteByUsername(username);
    }

    @Override
    public void changePassword(AdminChangePasswordDtoIn changePasswordDtoIn) {
        if(!changePasswordDtoIn.getNewPassword().equals(changePasswordDtoIn.getConfirmPassword())){
            throw new BadRequestException("New password and confirm password do not match");
        }
        User user = userRepository.findById(changePasswordDtoIn.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found: " + changePasswordDtoIn.getUserId()));
        user.setPassword(passwordEncoder.encode(changePasswordDtoIn.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public void changePassword(String username, ChangePasswordDtoIn changePasswordDtoIn) {
        if(!changePasswordDtoIn.getNewPassword().equals(changePasswordDtoIn.getConfirmPassword())){
            throw new BadRequestException("New password and confirm password do not match");
        }
        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new NotFoundException("User not found: " + username);
        }
        if (!passwordEncoder.matches(changePasswordDtoIn.getOldPassword(), user.getPassword())) {
            throw new BadRequestException("Old password is incorrect");
        }
        if(changePasswordDtoIn.getOldPassword().equals(changePasswordDtoIn.getNewPassword())){
            throw new BadRequestException("New password must be different from old password");
        }
        user.setPassword(passwordEncoder.encode(changePasswordDtoIn.getNewPassword()));
        userRepository.save(user);
    }

}
