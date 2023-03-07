package com.transport.transport.service;

import com.transport.transport.common.EndpointConstant;
import com.transport.transport.config.security.user.Account;
import com.transport.transport.model.request.account.*;
import com.transport.transport.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


public interface AccountService extends CRUDService<Account> {

    List<Account> findAllAccounts(Pageable pageable);
    Account updateStatus(Long id);
    Account findByUsername(String username);
    Account getAccountActiveByUsername(String username);
    List<Account> searchAccountsByFullName(String search, Pageable pageable);
    Account changePassword(Long id, ChangePasswordRequest changePasswordRequest);
    Account updateProfile(String username, UpdateRequest updateRequest);

    List<Account> findAccountByRoleAndStatus(String role, String status);
    Account uploadImg(MultipartFile image, String username);

}
