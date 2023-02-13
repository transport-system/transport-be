package com.transport.transport.mapper;

import com.transport.transport.model.entity.Account;
import com.transport.transport.model.request.account.RegisterRequest;
import com.transport.transport.model.response.account.AccountResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

public interface TripMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    AccountResponse mapAccountResponseFromAccount(Account account);

    @Mapping(target = "dateOfBirth", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void registerAccountFromRegisterRequest(@MappingTarget Account account, RegisterRequest registerRequest);

//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    @Mapping(target = "dateOfBirth", ignore = true)
//    void updateAccountFromUpdateRequest(@MappingTarget Account account, UpdateRequest updateRequest);
}
