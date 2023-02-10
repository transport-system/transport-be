package com.transport.transport.service.impl;

import com.transport.transport.common.RoleEnum;
import com.transport.transport.common.Status;
import com.transport.transport.model.entity.Account;
import com.transport.transport.model.request.account.ChangePasswordRequest;
import com.transport.transport.model.request.account.LoginRequest;
import com.transport.transport.model.request.account.RegisterRequest;
import com.transport.transport.model.response.account.AccountLoginResponse;
import com.transport.transport.repository.AccountRepository;
import com.transport.transport.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImp implements AccountService {

    private final AccountRepository repository;

    @Override
    public List<Account> findAllAccounts(Pageable pageable) {
        return repository.findAllByStatusIsNotNull(pageable);
    }

    @Override
    public List<Account> findAll() {
        return repository.findAll();
    }

    @Override
    public Account findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void save(Account entity) {
        repository.save(entity);
    }


    @Override
    public void delete(Long id) {
        Account account = repository.findById(id).orElse(null);
        if (account != null) {
            account.setStatus(Status.Account.INACTIVE.name());
            repository.save(account);
        }
    }

    @Override
    public void update(Account account) {
        repository.save(account);
    }

    @Override
    public Account findByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public Account getAccountActiveByUsername(String username) {
        return repository.findAccountByUsernameAndStatus(username, Status.Account.ACTIVE.name());
    }


    @Override
    public List<Account> searchAccountsByFullName(String search, Pageable pageable) {
        return repository.searchAccountsByFullName(search, pageable);
    }

    @Override
    public Account register(RegisterRequest registerRequest) {
        if (repository.existsByUsername(registerRequest.getUsername())) {
            throw new RuntimeException("Username already exists");
        } else if (repository.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException("Email already exists");
        } else if (repository.existsByPhone(registerRequest.getPhone())) {
            throw new RuntimeException("Phone already exists");
        } else if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
            throw new RuntimeException("Password and confirm password not match");
        } else {
            Account account = new Account();
            account.setUsername(registerRequest.getUsername());
            account.setPassword(registerRequest.getPassword());
            account.setFirstname(registerRequest.getFirstname());
            account.setLastname(registerRequest.getLastname());
            account.setGender(registerRequest.getGender());
            account.setDateOfBirth(registerRequest.getDateOfBirth());
            account.setEmail(registerRequest.getEmail());
            account.setPhone(registerRequest.getPhone());
            account.setRole(RoleEnum.USER);
            account.setStatus("ACTIVE");
            return repository.save(account);
        }
    }

    @Override
    public boolean login(LoginRequest loginRequest) {
        AccountLoginResponse accountLoginResponse = new AccountLoginResponse();
        if (repository.existsByUsername(loginRequest.getUsername())) {
            Account account = repository.findByUsername(loginRequest.getUsername());
            if (account.getPassword().equals(loginRequest.getPassword())) {
                Account account1 = repository.findById(account.getId()).get();
                accountLoginResponse.setMessage("Login success");
                return true;
            } else {
                accountLoginResponse.setMessage("username or password is wrong");
                return false;
            }
        } else {
            throw new RuntimeException("Username not exists");
        }
    }

    @Override
    public Account changePassword(Long id, ChangePasswordRequest changePasswordRequest) {
        if (repository.existsById(id)) {
            Account account = repository.findById(id).get();
            if (account.getPassword().equals(changePasswordRequest.getOldPassword())) {
                if (changePasswordRequest.getNewPassword().equals(changePasswordRequest.getOldPassword())) {
                    throw new RuntimeException("New password and old password not the same");
                }
                account.setPassword(changePasswordRequest.getNewPassword());
                    return repository.save(account);
            } else {
                throw new RuntimeException("Old password not match");
            }
        } else {
            throw new RuntimeException("Account not exists");
        }
    }
}
