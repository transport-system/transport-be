package com.transport.transport.controller;

import com.transport.transport.common.EndpointConstant;
import com.transport.transport.mapper.AccountMapper;
import com.transport.transport.model.entity.Account;
import com.transport.transport.model.request.account.ChangePasswordRequest;
import com.transport.transport.model.request.account.LoginRequest;
import com.transport.transport.model.request.account.RegisterRequest;
import com.transport.transport.model.response.account.AccountLoginResponse;
import com.transport.transport.model.response.account.AccountResponse;
import com.transport.transport.model.response.error.MsgResponse;
import com.transport.transport.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private final AccountMapper accountMapper;

    @PostMapping(path = EndpointConstant.Authentication.LOGIN_ENDPOINT)
    public ResponseEntity<AccountLoginResponse> checkLogin(@Valid @RequestBody LoginRequest loginRequest) {

        if (accountService.login(loginRequest)) {
            Account account = accountService.findByUsername(loginRequest.getUsername());
            AccountResponse accountResponse = accountMapper.toAccountResponse(account);
            return new ResponseEntity<>(new AccountLoginResponse("Login success", accountResponse), null, 200);
        } else {
            return new ResponseEntity<>(new AccountLoginResponse("username or password invalid"), null, 400);
        }
    }

    @PostMapping(path = EndpointConstant.Authentication.REGISTER_ENDPOINT)
    public ResponseEntity<AccountResponse> register(@Valid @RequestBody RegisterRequest request) {

        Account account = accountService.register(request);
        AccountResponse response = accountMapper.toAccountResponse(account);
        return new ResponseEntity<>(response, null, 200);
    }



    @PatchMapping(path = EndpointConstant.Account.ACCOUNT_CHANGE_ENDPOINT+"/{id}")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordRequest request,
                                                          @PathVariable(name = "id") Long id) {
        Account account = accountService.changePassword(id, request);
        AccountResponse response = accountMapper.toAccountResponse(account);
        return new ResponseEntity<>(new MsgResponse("Login successfully"), null, 200);
    }
}
