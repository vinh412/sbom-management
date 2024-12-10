package com.vinhdd.sbom.api.service.impl;

import com.vinhdd.sbom.api.dto.UserDto;
import com.vinhdd.sbom.api.dto.in.PageRequestDtoIn;
import com.vinhdd.sbom.api.dto.in.UserDtoIn;
import com.vinhdd.sbom.api.exception.BadRequestException;
import com.vinhdd.sbom.api.exception.NotFoundException;
import com.vinhdd.sbom.api.model.Role;
import com.vinhdd.sbom.api.model.User;
import com.vinhdd.sbom.api.repository.RoleRepository;
import com.vinhdd.sbom.api.repository.UserRepository;
import com.vinhdd.sbom.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Page<UserDto> getAllBySearchString(PageRequestDtoIn pageRequestDtoIn) {
        Sort sort = Sort.by(pageRequestDtoIn.getSortBy());
        if (pageRequestDtoIn.getOrder().equals("desc")) {
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
    public UserDto create(UserDtoIn userDtoIn) {
        User user = new User();
        user.setUsername(userDtoIn.getUsername());
        user.setPassword(passwordEncoder.encode("123456"));
        user.setFullname(userDtoIn.getFullname());
        user.setEmail(userDtoIn.getEmail());
        user.setPhoneNumber(userDtoIn.getPhoneNumber());
        user.setDescription(userDtoIn.getDescription());
        Set<Role> roles = new HashSet<>();
        for (String roleName : userDtoIn.getRoles()) {
            Role role = roleRepository.findByName(roleName)
                    .orElseThrow(() -> new BadRequestException("Role not found: " + roleName));
            roles.add(role);
        }
        user.setRoles(roles);

        User savedUser = userRepository.save(user);
        return UserDto.from(savedUser);
    }

    @Override
    public UserDto update(UserDtoIn userDtoIn) {
        User user = userRepository.findByUsername(userDtoIn.getUsername());
        user.setFullname(userDtoIn.getFullname());
        user.setEmail(userDtoIn.getEmail());
        user.setPhoneNumber(userDtoIn.getPhoneNumber());
        user.setDescription(userDtoIn.getDescription());
        user.getRoles().clear();
        for (String roleName : userDtoIn.getRoles()) {
            Role role = roleRepository.findByName(roleName)
                    .orElseThrow(() -> new BadRequestException("Role not found: " + roleName));
            user.getRoles().add(role);
        }

        User savedUser = userRepository.save(user);
        return UserDto.from(savedUser);

    }

    @Override
    public void delete(String username) {
        userRepository.deleteByUsername(username);
    }

}
