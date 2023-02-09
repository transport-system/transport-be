package com.transport.transport.service;

import com.transport.transport.model.entity.Account;

import java.util.Optional;


public interface AccountService {
    Optional<Account> save(Account account);
}
