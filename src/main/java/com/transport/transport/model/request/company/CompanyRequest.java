package com.transport.transport.model.request.company;

import com.transport.transport.utils.Trimmable;
import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyRequest implements Trimmable {
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

    @NotEmpty(message = "Gender is required")
    private String gender;
    private String status = "ACTIVE";

    @NotBlank
    @Max(value = 255, message = "must be less than or equal to 255")
    @Min(value = 20, message = "must be greater than or equal to 255")
    private String companyName;

    @NotBlank
    @Max(value = 1000, message = "must be less than or equal to 1000")
    @Min(value = 50, message = "must be greater than or equal to 50")
    private String description;
}
