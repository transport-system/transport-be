package com.transport.transport.service.impl.authen;

import com.transport.transport.common.RoleEnum;
import com.transport.transport.common.Status;
import com.transport.transport.common.TokenType;
import com.transport.transport.config.security.jwt.JwtService;
import com.transport.transport.config.security.user.Account;
import com.transport.transport.exception.BadRequestException;
import com.transport.transport.model.entity.Token;
import com.transport.transport.model.request.authen.AuthenticationRequest;
import com.transport.transport.model.request.authen.RegisterRequestV2;
import com.transport.transport.model.request.company.CompanyRequest;
import com.transport.transport.model.response.Authen.AuthenticationResponse;
import com.transport.transport.repository.AccountRepository;
import com.transport.transport.repository.TokenRepository;
import com.transport.transport.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImp implements AuthenticationService {
    private final AccountRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    @Override
    public AuthenticationResponse register(RegisterRequestV2 request) {
//        long milliseconds = request.getDateOfBirth();
//        Date dob = ConvertUtils.getDate(milliseconds);

        var user = Account.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .username(request.getUsername())
                .phone(request.getPhone())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .dateOfBirth(request.getDateOfBirth())
                .avatarImage(request.getAvatarImage())
                .gender(request.getGender())
                .role(RoleEnum.USER.name())
                .status(Status.Account.ACTIVE.name())
                .build();
        return getAuthenticationResponse(request, user);
    }

    @Override
    public AuthenticationResponse admin(RegisterRequestV2 request) {
//        long milliseconds = request.getDateOfBirth();
//        Date dob = ConvertUtils.getDate(milliseconds);

        var user = Account.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .username(request.getUsername())
                .phone(request.getPhone())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .dateOfBirth(request.getDateOfBirth())
                .avatarImage(request.getAvatarImage())
                .gender(request.getGender())
                .role(RoleEnum.ADMIN.name())
                .status(Status.Account.ACTIVE.name())
                .build();
        return getAuthenticationResponse(request, user);
    }

    private AuthenticationResponse getAuthenticationResponse(RegisterRequestV2 request, Account user) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new BadRequestException("Password and confirm password not match");
        }
        else if (repository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already exists");
        } else if (repository.existsByPhone(request.getPhone())) {
            throw new BadRequestException("Phone already exists");
        }
        var savedUser = repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .username(request.getUsername())
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .role(user.getRole())
                .token(jwtToken)
                .build();
    }

    @Override
    public Account registerCompany(CompanyRequest request) {
//        long milliseconds = request.getDateOfBirth();
//        Date dob = ConvertUtils.getDate(milliseconds);

        var user = Account.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .username(request.getUsername())
                .phone(request.getPhone())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .avatarImage(request.getAvatarImage())
                .gender(request.getGender())
                .role(RoleEnum.COMPANY.name())
                .status(Status.Account.ACTIVE.name())
                .build();
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new BadRequestException("Password and confirm password not match");
        }
        else if (repository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already exists");
        } else if (repository.existsByPhone(request.getPhone())) {
            throw new BadRequestException("Phone already exists");
        };
        var savedUser = repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        saveUserToken(savedUser, jwtToken);
        AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
        return user;
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = repository.findByUsername(request.getUsername())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);

        return AuthenticationResponse.builder()
                .id(user.getId())
                .username(request.getUsername())
                .role(user.getRole())
                .token(jwtToken)
                .build();
    }

    @Override
    public void saveUserToken(Account user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER.name())
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    @Override
    public void revokeAllUserTokens(Account user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
}
