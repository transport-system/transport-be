package com.transport.transport.mapper;

import com.transport.transport.model.entity.Account;
import com.transport.transport.model.response.account.AccountResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", config = ConfigurationMapper.class)
public interface AccountMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    AccountResponse toAccountResponse(Account account);
}
