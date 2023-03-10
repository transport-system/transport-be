package com.transport.transport.service.impl.company;

import com.transport.transport.common.RoleEnum;
import com.transport.transport.common.Status;
import com.transport.transport.config.security.user.Account;
import com.transport.transport.exception.NotFoundException;
import com.transport.transport.mapper.CompanyMapper;
import com.transport.transport.model.entity.Company;
import com.transport.transport.model.request.company.CompanyRequest;
import com.transport.transport.model.request.company.CompanyUpdateRequest;
import com.transport.transport.repository.AccountRepository;
import com.transport.transport.repository.CompanyRepository;
import com.transport.transport.service.AuthenticationService;
import com.transport.transport.service.CompanyService;
import com.transport.transport.utils.ConvertUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyServiceImp implements CompanyService {

    private final CompanyRepository repository;
    private final AccountRepository accountRepository;
    private final AuthenticationService authenticationService;
    private final CompanyMapper mapper;


    @Override
    public List<Company> findAll() {
        return repository.findAll();
    }

    @Override
    public Company findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Company not found: " + id));
    }

    @Override
    public void save(Company entity) {
        repository.save(entity);
    }

    @Override
    public void delete(Long id) {

        Company company = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Company not found: " + id));
        Account account = company.getAccount();

        account.setStatus(Status.Account.INACTIVE.name());

        accountRepository.save(account);
    }

    @Override
    public void update(Company entity) {
        repository.save(entity);
    }

    @Override
    public void updateCompany(CompanyUpdateRequest request, Long id) {
        Company company = repository.findCompanyByAccount_Id(id);
        mapper.updateCompanyFromUpdateRequest(request, company);
        repository.save(company);
    }

    @Override
    public Company registerCompany(CompanyRequest request) {
        Account account = authenticationService.registerCompany(request);
        Company company = repository.findCompanyByAccount_Username(request.getUsername());
        if (company != null) {
            throw new NotFoundException("Company username existed : " + account.getUsername());
        }
        company = new Company();
        company.setAccount(account);
        mapper.registerCompanyFromCompanyRequest(request, company);
        return repository.save(company);
    }
}

