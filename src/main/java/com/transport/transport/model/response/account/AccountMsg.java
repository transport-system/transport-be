package com.transport.transport.model.response.account;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.transport.transport.common.RoleEnum;
import com.transport.transport.common.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountMsg {
    private String message;
    private RoleEnum role;
    private Status.Account status;
    private List<AccountResponse> list;
    private AccountResponse data;


    public AccountMsg(String message) {
        this.message = message;
    }

    public AccountMsg(String message, AccountResponse accountResponse) {
        this.message = message;
        this.data = accountResponse;
    }

    public AccountMsg(String message, RoleEnum role, Status.Account status, List<AccountResponse> list) {
        this.message = message;
        this.role = role;
        this.status = status;
        this.list = list;
    }
}
