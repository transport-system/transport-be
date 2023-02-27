package com.transport.transport.service.impl.account;

import com.transport.transport.common.EndpointConstant;
import com.transport.transport.common.RoleEnum;
import com.transport.transport.common.Status;
import com.transport.transport.config.security.user.Account;
import com.transport.transport.exception.NotFoundException;
import com.transport.transport.mapper.AccountMapper;
import com.transport.transport.model.request.account.ChangePasswordRequest;
import com.transport.transport.model.request.account.RegisterRequest;
import com.transport.transport.model.request.account.UpdateRequest;
import com.transport.transport.repository.AccountRepository;
import com.transport.transport.repository.CompanyRepository;
import com.transport.transport.service.AccountService;
import com.transport.transport.utils.ConvertUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImp implements AccountService {
    private static final Path CURRENT_FOLDER = Paths.get(System.getProperty("user.dir"));
    private final AccountRepository repository;
    private final CompanyRepository companyRepository;
    private final AccountMapper mapper;

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
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Account id not found: " + id));
    }

    @Override
    public Account updateStatus(Long id) {
        if (repository.existsById(id)) {
            Account account = repository.findById(id).get();
            account.setStatus(Status.Account.ACTIVE.name());
            save(account);
            return account;
        } else {
            throw new NotFoundException("Not found with id: " + id);
        }
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
        return repository.findByUsername(username).orElseThrow(() -> new NotFoundException("Account username not found: " + username));
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
            mapper.registerAccountFromRegisterRequest(account, registerRequest);

            long milliseconds = registerRequest.getDateOfBirth();
            Date dob = ConvertUtils.getDate(milliseconds);
            account.setDateOfBirth(dob);

            account.setRole(RoleEnum.USER.name());
            return repository.save(account);
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

    @Override
    public Account updateProfile(String username, UpdateRequest updateRequest) {
        Account account = findByUsername(username);
        mapper.updateAccountFromUpdateRequest(account, updateRequest);
        long milliseconds = updateRequest.getDateOfBirth();
        Date dob = ConvertUtils.getDate(milliseconds);
        account.setDateOfBirth(dob);

        return repository.save(account);
    }

    @Override
    public List<Account> findAccountByRoleAndStatus(String role, String status) {

        List<Account> rolesList = repository.getAccountsByRole(role).stream().toList();
        List<Account> statusList = repository.getAccountsByRole(status).stream().toList();
        if (rolesList.equals(role.equalsIgnoreCase("admin"))) {
            return repository.getAccountsByRoleAndStatus(RoleEnum.ADMIN.name(), status);
        } else if (rolesList.equals(role.equalsIgnoreCase("user"))
                && statusList.equals(status.equalsIgnoreCase("active"))) {
            return repository.getAccountsByRoleAndStatus(RoleEnum.USER.name(), Status.Account.ACTIVE.name());
        } else if (rolesList.equals(role.equalsIgnoreCase("company"))
                && statusList.equals(status.equalsIgnoreCase("active"))) {
            return repository.getAccountsByRoleAndStatus(RoleEnum.COMPANY.name(), Status.Account.ACTIVE.name());
        } else if (rolesList.equals(role.equalsIgnoreCase("company"))
                && statusList.equals(status.equalsIgnoreCase("inactive"))) {
            return repository.getAccountsByRoleAndStatus(RoleEnum.COMPANY.name(), Status.Account.INACTIVE.name());
        }

        return repository.getAccountsByRoleAndStatus(role, status);

    }

    @Override
    public Account uploadImg(Long id, MultipartFile image) throws IOException {
        Path staticPath = Paths.get("static");
        Path imagePath = Paths.get("images");
        if (!Files.exists(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath))) {
            Files.createDirectories(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath));
        }
        Path file = CURRENT_FOLDER.resolve(staticPath)
                .resolve(imagePath).resolve(image.getOriginalFilename());
        try (OutputStream os = Files.newOutputStream(file)) {
            os.write(image.getBytes());
        }
        Account user = repository.findById(id).get();
        user.setAvatarImage(imagePath.resolve(image.getOriginalFilename()).toString());
        return repository.save(user);
    }

}
