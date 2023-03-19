package com.transport.transport.model.request.account;

import com.transport.transport.utils.Trimmable;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRequest implements Trimmable {
    private String firstname;

    private String lastname;

    private String gender;

    private String avatarImage;

    private LocalDate dateOfBirth;
}
