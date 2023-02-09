package com.transport.transport.model.response.account;

import com.transport.transport.common.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private String type = "Bearer";
    private String username;
    private RoleEnum roleName;
    private String name;
    private String avatarImage;
    private Date dateOfBirth;
    private String email;
    private String phone;
    private String gender;
    private String status;
    private String token;
}
