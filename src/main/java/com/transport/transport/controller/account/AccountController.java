package com.transport.transport.controller.account;

import com.transport.transport.common.EndpointConstant;
import com.transport.transport.config.security.jwt.JwtService;
import com.transport.transport.config.security.user.Account;
import com.transport.transport.mapper.AccountMapper;
import com.transport.transport.model.request.account.ChangePasswordRequest;
import com.transport.transport.model.request.account.UpdateRequest;
import com.transport.transport.model.response.account.AccountMsg;
import com.transport.transport.model.response.account.AccountResponse;
import com.transport.transport.model.response.error.MsgResponse;
import com.transport.transport.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private final AccountMapper accountMapper;
    private final JwtService jwtService;

    @GetMapping(path = EndpointConstant.Account.ACCOUNT_ENDPOINT + "/{id}")
    public ResponseEntity<?> getById(@PathVariable(name = "id") Long id) {
        Account account = accountService.findById(id);
        AccountResponse response = accountMapper.mapAccountResponseFromAccount(account);
        return new ResponseEntity<>(new AccountMsg("Get account success", response), null, 200);
    }

    @PreAuthorize("hasAuthority(T(com.transport.transport.common.RoleEnum).ADMIN)")
    @GetMapping(path = EndpointConstant.Account.ACCOUNT_ENDPOINT )
    public ResponseEntity<?> getAll() {
        List<Account> account = accountService.findAll();
        List<AccountResponse> accountResponses = accountMapper.mapAccountResponseFromAccount(account);
        return new ResponseEntity<>(accountResponses, null, 200);
    }

    @PatchMapping(path = EndpointConstant.Account.ACCOUNT_ENDPOINT + "change" + "/{id}")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordRequest request,
                                            @PathVariable(name = "id") Long id) {
        Account account = accountService.changePassword(id, request);
        return new ResponseEntity<>(new MsgResponse("Change successfully"), null, 200);
    }

    @PatchMapping(path = EndpointConstant.Account.ACCOUNT_ENDPOINT)
    public ResponseEntity<?> updateAccount(@Valid @RequestBody UpdateRequest request,
                                           @RequestHeader(value = AUTHORIZATION) String token) {
        token = token.substring("Bearer ".length());
        String username = jwtService.extractUsername(token); //Lấy username từ token

        Account account = accountService.updateProfile(username, request);
        AccountResponse response = accountMapper.mapAccountResponseFromAccount(account);
        return new ResponseEntity<>(new AccountMsg("Update Account Successfully", response), null, 200);
    }

    @PreAuthorize("hasAuthority(T(com.transport.transport.common.RoleEnum).ADMIN)")
    @GetMapping(path = EndpointConstant.Account.ACCOUNT_ENDPOINT + "/{role}/{status}")
    public ResponseEntity<?> findAccountByRoleAndStatus(
            @PathVariable(name = "role") String role,
            @PathVariable(name = "status") String status) {
        List<Account> accounts = accountService.findAccountByRoleAndStatus(role, status);
        List<AccountResponse> response = accountMapper.mapAccountResponseFromAccount(accounts);
        return new ResponseEntity<>(new AccountMsg("GET Role vs Status Successfully", role, status, response), null, 200);
    }
}
