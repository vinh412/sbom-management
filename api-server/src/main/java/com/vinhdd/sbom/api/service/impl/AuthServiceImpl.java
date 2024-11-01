package com.vinhdd.sbom.api.service.impl;

import com.vinhdd.sbom.api.config.JwtService;
import com.vinhdd.sbom.api.dto.out.AuthResponse;
import com.vinhdd.sbom.api.exception.CommonException;
import com.vinhdd.sbom.api.model.User;
import com.vinhdd.sbom.api.repository.UserRepository;
import com.vinhdd.sbom.api.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    @Value("${jwt.EXPIRATION_TIME:3600000}")
    private int JWT_EXPIRATION_TIME;
    @Value("${jwt.EXPIRATION_TIME_REMEMBER_ME:3600000}")
    private int JWT_EXPIRATION_TIME_REMEMBER_ME;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public AuthResponse login(String username, String password) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtService.generateToken((UserDetails) authentication.getPrincipal(), JWT_EXPIRATION_TIME);
            return new AuthResponse(token);
    }

    @Override
    public void register(String username, String password) {
            User existingUser = userRepository.findByUsername(username);
            if (existingUser != null) {
                throw new CommonException("username exist", HttpStatus.CONFLICT);
            }
            User user = new User();
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user);
    }
}
