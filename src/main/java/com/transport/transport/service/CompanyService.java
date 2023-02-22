package com.transport.transport.service;

import com.transport.transport.model.entity.Account;
import com.transport.transport.model.entity.Company;
import com.transport.transport.model.request.company.CompanyRequest;
import com.transport.transport.model.request.company.CompanyUpdateRequest;

public interface CompanyService extends CRUDService<Company> {
    void updateCompany(CompanyUpdateRequest request, Long id);
    Company registerCompany(CompanyRequest registerCompanyRequest);
    Account addNewAccount(CompanyRequest registerCompanyRequest);

}
