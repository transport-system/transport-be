package com.transport.transport.service.impl;

import com.transport.transport.common.RoleEnum;
import com.transport.transport.config.jwt.JwtService;
import com.transport.transport.model.entity.Account;
import com.transport.transport.model.request.account.AuthenticationRequest;
import com.transport.transport.model.request.account.RegisterRequest;
import com.transport.transport.model.response.account.AuthenticationResponse;
import com.transport.transport.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AccountRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = Account.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .phone(request.getPhone())
                .avatarImage(request.getAvatarImage())
                .dateOfBirth(request.getDateOfBirth())
                .gender(request.getGender())
                .status(request.getStatus())
                .role(RoleEnum.USER)
                .build();
        repository.save(user);
        return getAuthenticationResponse(user);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = repository.findByUsername(request.getUsername())
                .orElseThrow();
        return getAuthenticationResponse(user);
    }

    private AuthenticationResponse getAuthenticationResponse(Account user) {
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .username(user.getUsername())
                .name(user.getFirstname() + " " + user.getLastname())
                .type("Bearer")
                .avatarImage(user.getAvatarImage())
                .dateOfBirth(user.getDateOfBirth())
                .phone(user.getPhone())
                .email(user.getEmail())
                .gender(user.getGender())
                .status(user.getStatus())
                .roleName(RoleEnum.USER)
                .build();
    }
}