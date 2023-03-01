package com.transport.transport.model.response.account;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountResponse {
    private Long id;
    private String username;
    private String role;
    private String firstname;
    private String lastname;
    private String avatarImage;
    private LocalDate dateOfBirth;
    private String email;
    private String phone;
    private String gender;
    private Long companyId;
}
