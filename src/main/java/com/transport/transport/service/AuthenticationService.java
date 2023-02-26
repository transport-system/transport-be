package com.transport.transport.service;

import com.transport.transport.config.security.user.Account;
import com.transport.transport.model.request.authen.AuthenticationRequest;
import com.transport.transport.model.request.authen.RegisterRequestV2;
import com.transport.transport.model.request.company.CompanyRequest;
import com.transport.transport.model.response.Authen.AuthenticationResponse;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequestV2 request);
    AuthenticationResponse admin(RegisterRequestV2 request);
    Account registerCompany(CompanyRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);
    void saveUserToken(Account user, String jwtToken);
    void revokeAllUserTokens(Account user);
}
