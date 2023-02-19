package com.transport.transport.model.response.account;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.transport.transport.model.entity.Company;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountResponse {
    private Long id;
    private String username;
    @NotBlank
    private String roleName;
    private String firstname;
    private String lastname;
    private String avatarImage;
    private Date dateOfBirth;
    private String email;
    private String phone;
    private String gender;
    private Long companyId;
}
