package com.transport.transport.service;

import com.transport.transport.model.entity.Account;
import com.transport.transport.model.request.account.ChangePasswordRequest;
import com.transport.transport.model.request.account.LoginRequest;
import com.transport.transport.model.request.account.RegisterRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface AccountService extends CRUDService<Account> {

    List<Account> findAllAccounts(Pageable pageable);
    Account findById(Long id);
    void delete(Long id);
    void update(Account account);
    Account findByUsername(String username);
    Account getAccountActiveByUsername(String username);

    List<Account> searchAccountsByFullName(String search, Pageable pageable);
    Account register(RegisterRequest registerRequest);

    boolean login(LoginRequest loginRequest);

    Account changePassword(Long id, ChangePasswordRequest changePasswordRequest);

}
