package com.transport.transport.controller.authen;

import com.transport.transport.common.EndpointConstant;
import com.transport.transport.model.request.authen.AuthenticationRequest;
import com.transport.transport.model.request.authen.RegisterRequestV2;
import com.transport.transport.model.response.Authen.AuthenMsg;
import com.transport.transport.model.response.Authen.AuthenticationResponse;
import com.transport.transport.service.AuthenticationService;
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
    public ResponseEntity<?> register(
            @RequestBody RegisterRequestV2 request
    ) {
        AuthenticationResponse response = service.register(request);
        return new ResponseEntity<>(new AuthenMsg("Register successfully", response), null, 201);
    }

    @PostMapping(path = EndpointConstant.Authentication.REGISTER_ENDPOINT + "/admin")
    public ResponseEntity<?> adminRegister(
            @RequestBody RegisterRequestV2 request
    ) {
        AuthenticationResponse response = service.admin(request);
        return new ResponseEntity<>(new AuthenMsg("Register successfully", response), null, 201);
    }

    @PostMapping(path = EndpointConstant.Authentication.LOGIN_ENDPOINT)
    public ResponseEntity<?> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        AuthenticationResponse response = service.authenticate(request);
        return new ResponseEntity<>(new AuthenMsg("Get successfully", response), null, 200);
    }
}
