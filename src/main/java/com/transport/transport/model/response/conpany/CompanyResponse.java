package com.transport.transport.model.response.conpany;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.transport.transport.model.response.account.AccountResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompanyResponse {
    private Long companyId;
    private AccountResponse account;
    private String name;
    private String description;
    private String status;
    private double rating;
}
