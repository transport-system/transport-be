package com.transport.transport.controller.account;

import com.transport.transport.common.EndpointConstant;
import com.transport.transport.common.RoleEnum;
import com.transport.transport.common.Status;
import com.transport.transport.mapper.AccountMapper;
import com.transport.transport.model.entity.Account;
import com.transport.transport.model.request.account.ChangePasswordRequest;
import com.transport.transport.model.request.account.LoginRequest;
import com.transport.transport.model.request.account.RegisterRequest;
import com.transport.transport.model.request.account.UpdateRequest;
import com.transport.transport.model.response.account.AccountMsg;
import com.transport.transport.model.response.account.AccountResponse;
import com.transport.transport.model.response.error.MsgResponse;
import com.transport.transport.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private final AccountMapper accountMapper;

    @PostMapping(path = EndpointConstant.Authentication.LOGIN_ENDPOINT)
    public ResponseEntity<?> checkLogin(@Valid @RequestBody LoginRequest loginRequest) {

        if (accountService.login(loginRequest)) {
            Account account = accountService.findByUsername(loginRequest.getUsername());
            AccountResponse accountResponse = accountMapper.mapAccountResponseFromAccount(account);
            return new ResponseEntity<>(new AccountMsg("Login success", accountResponse), null, 200);
        } else {
            return new ResponseEntity<>(new AccountMsg("username or password invalid"), null, 400);
        }
    }

    @PostMapping(path = EndpointConstant.Authentication.REGISTER_ENDPOINT)
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {

        Account account = accountService.register(request);
        AccountResponse response = accountMapper.mapAccountResponseFromAccount(account);
        return new ResponseEntity<>(new AccountMsg("register success", response), null, 200);
    }

    @PatchMapping(path = EndpointConstant.Account.ACCOUNT_CHANGE_ENDPOINT + "/{id}")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordRequest request,
                                            @PathVariable(name = "id") Long id) {
        Account account = accountService.changePassword(id, request);
        AccountResponse response = accountMapper.mapAccountResponseFromAccount(account);
        return new ResponseEntity<>(new MsgResponse("Change successfully"), null, 200);
    }

    @PatchMapping(path = EndpointConstant.Account.ACCOUNT_ENDPOINT + "/{id}")
    public ResponseEntity<?> updateAccount(@Valid @RequestBody UpdateRequest request,
                                           @PathVariable(name = "id") Long id) {
        Account account = accountService.updateProfile(id, request);
        AccountResponse response = accountMapper.mapAccountResponseFromAccount(account);
        return new ResponseEntity<>(new AccountMsg("Update Account Successfully", response), null, 200);
    }

    @GetMapping(path = EndpointConstant.Account.ACCOUNT_ENDPOINT + "/{role}/{status}")
    public ResponseEntity<?> findAccountByRoleAndStatus(
            @PathVariable(name = "role") RoleEnum role,
            @PathVariable(name = "status") Status.Account status) {
        List<Account> accounts = accountService.findAccountByRoleAndStatus(role, status);
        List<AccountResponse> response = accountMapper.mapAccountResponseFromAccount(accounts);
        return new ResponseEntity<>(new AccountMsg("GET Role vs Status Successfully", role, status, response), null, 200);
    }
}
