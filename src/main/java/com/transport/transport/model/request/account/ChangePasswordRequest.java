package com.transport.transport.model.request.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ChangePasswordRequest {


    @NotBlank
    private String oldPassword;

    @NotBlank
    private String newPassword;
}
