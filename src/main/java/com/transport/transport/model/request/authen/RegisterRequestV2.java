package com.transport.transport.model.request.authen;

import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestV2 {
    @NotBlank
    private String username;

    @NotBlank
    @Size(min = 6, max = 20, message = "must be between 6 and 20 characters")
    private String password;

    @NotBlank
    private String confirmPassword;

    @NotBlank
    private String firstname;

    @NotBlank
    private String lastname;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = "(0)+(\\d){9}", message = "is invalid")
    private String phone;

    private String avatarImage;

    private LocalDate dateOfBirth;

    @NotEmpty(message = "Gender is required")
    private String gender;
    private String status = "ACTIVE";
}
