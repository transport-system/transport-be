package com.transport.transport.mapper;

import com.transport.transport.config.security.user.Account;
import com.transport.transport.model.entity.Company;
import com.transport.transport.model.request.company.CompanyRequest;
import com.transport.transport.model.request.company.CompanyUpdateRequest;
import com.transport.transport.model.response.conpany.CompanyResponse;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", config = ConfigurationMapper.class)
public interface CompanyMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCompanyFromUpdateRequest(CompanyUpdateRequest request, @MappingTarget Company company);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "companyName", target = "companyName")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "dateOfBirth", target = "account.dateOfBirth", ignore = true)
    void registerCompanyFromCompanyRequest(CompanyRequest companyRequest, @MappingTarget Company company);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)

    @Mapping(source = "dateOfBirth", target = "account.dateOfBirth", ignore = true)
    @Mapping(source = "username", target = "username")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "firstname", target = "firstname")
    @Mapping(source = "lastname", target = "lastname")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "avatarImage", target = "avatarImage")
    void registerAccountFromCompanyRequest(CompanyRequest companyRequest, @MappingTarget Account account);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "companyName", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "id", target = "companyId")
    @Mapping(source = "account.status", target = "status")
    CompanyResponse mapToCompanyResponse(Company company);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @InheritConfiguration(name = "mapToCompanyResponse")
    List<CompanyResponse> mapToCompanyResponse(List<Company> company);


}
