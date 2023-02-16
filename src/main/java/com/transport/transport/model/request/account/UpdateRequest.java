package com.transport.transport.model.request.account;

import com.transport.transport.utils.Trimmable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateRequest implements Trimmable {
    private String firstname;

    private String lastname;

    private String gender;

    private String avatarImage;

    private Long dateOfBirth;
}
