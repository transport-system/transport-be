package com.transport.transport.mapper;

import com.transport.transport.model.entity.Account;
import com.transport.transport.model.request.account.RegisterRequest;
import com.transport.transport.model.request.account.UpdateRequest;
import com.transport.transport.model.response.account.AccountResponse;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", config = ConfigurationMapper.class)
public interface AccountMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    AccountResponse mapAccountResponseFromAccount(Account account);

    @Mapping(target = "dateOfBirth", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void registerAccountFromRegisterRequest(@MappingTarget Account account, RegisterRequest registerRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "dateOfBirth", ignore = true)
    void updateAccountFromUpdateRequest(@MappingTarget Account account, UpdateRequest updateRequest);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @InheritConfiguration(name = "mapAccountResponseFromAccount")
    List<AccountResponse> mapAccountResponseFromAccount(List<Account> account);
}
