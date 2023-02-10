package com.transport.transport.model.response.account;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountLoginResponse  {
    private String message;
    private AccountResponse data;
    public AccountLoginResponse(String message) {
        this.message = message;
    }
    public AccountLoginResponse(String message, AccountResponse accountResponse) {
        this.message = message;
        this.data = accountResponse;
    }
}
