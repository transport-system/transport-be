package com.transport.transport.model.request.account;

import lombok.*;

import java.sql.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private String avatarImage;
    private Date dateOfBirth;
    private String gender;
    private String status = "PENDING";
}
