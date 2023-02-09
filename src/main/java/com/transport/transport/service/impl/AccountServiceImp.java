package com.transport.transport.service.impl;

import com.transport.transport.model.entity.Account;
import com.transport.transport.repository.AccountRepository;
import com.transport.transport.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImp implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Optional<Account> save(Account account) {
        return null;
    }

}
