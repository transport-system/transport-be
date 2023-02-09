package com.transport.transport.controller;

import com.transport.transport.common.EndpointConstant;
import com.transport.transport.model.request.account.AuthenticationRequest;
import com.transport.transport.model.request.account.RegisterRequest;
import com.transport.transport.model.response.account.AuthenticationResponse;
import com.transport.transport.service.impl.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping(path = EndpointConstant.Authentication.REGISTER_ENDPOINT)
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }
    @PostMapping(path = EndpointConstant.Authentication.LOGIN_ENDPOINT)
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }
}
