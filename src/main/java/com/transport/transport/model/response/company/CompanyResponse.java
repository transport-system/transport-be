package com.transport.transport.model.response.company;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.transport.transport.model.response.Authen.AuthenticationResponse;
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
    private AuthenticationResponse account;
    private String name;
    private String description;
    private String status;
    private double rating;
}
