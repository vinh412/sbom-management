package com.vinhdd.sbom.api.service.impl;

import com.vinhdd.sbom.api.exception.CommonException;
import com.vinhdd.sbom.api.model.CustomUserDetails;
import com.vinhdd.sbom.api.model.User;
import com.vinhdd.sbom.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null) {
            throw new CommonException("User not found: " + username, HttpStatus.UNAUTHORIZED);
        }
        if(user.getStatus() == 0) {
            throw new CommonException("User is disabled", HttpStatus.FORBIDDEN);
        }
        return new CustomUserDetails(user);
    }
}
