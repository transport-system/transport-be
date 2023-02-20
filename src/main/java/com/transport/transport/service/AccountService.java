package com.transport.transport.service;

import com.transport.transport.common.RoleEnum;
import com.transport.transport.common.Status;
import com.transport.transport.model.entity.Account;
import com.transport.transport.model.request.account.*;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface AccountService extends CRUDService<Account> {

    List<Account> findAllAccounts(Pageable pageable);
    Account updateStatus(Long id);
    Account findByUsername(String username);
    Account getAccountActiveByUsername(String username);
    List<Account> searchAccountsByFullName(String search, Pageable pageable);
    Account register(RegisterRequest registerRequest);
    boolean login(LoginRequest loginRequest);
    Account changePassword(Long id, ChangePasswordRequest changePasswordRequest);
    Account updateProfile(Long id, UpdateRequest updateRequest);

    List<Account> findAccountByRoleAndStatus(String role, String status);
}
