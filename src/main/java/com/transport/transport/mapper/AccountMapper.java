package com.transport.transport.mapper;

import com.transport.transport.config.security.user.Account;
import com.transport.transport.model.request.account.RegisterRequest;
import com.transport.transport.model.request.account.UpdateRequest;
import com.transport.transport.model.response.account.AccountResponse;
import org.mapstruct.*;

import java.time.LocalDate;
import java.util.List;

@Mapper(componentModel = "spring", config = ConfigurationMapper.class)
public interface AccountMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "dateOfBirth", source = "dateOfBirth")
    @Mapping(source = "role",target = "role")
    @Mapping(source = "company.id",target = "companyId")
    AccountResponse mapAccountResponseFromAccount(Account account);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "dateOfBirth", source = "dateOfBirth")
    void registerAccountFromRegisterRequest(@MappingTarget Account account, RegisterRequest registerRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "dateOfBirth", source = "dateOfBirth")
    void updateAccountFromUpdateRequest(@MappingTarget Account account, UpdateRequest updateRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @InheritConfiguration(name = "mapAccountResponseFromAccount")
    List<AccountResponse> mapAccountResponseFromAccount(List<Account> account);
}
