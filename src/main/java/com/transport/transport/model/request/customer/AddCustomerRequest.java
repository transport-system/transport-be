package com.transport.transport.model.request.customer;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddCustomerRequest {
    private String email;
    private String firstname;
    private String lastname;
    private String note;
    private String phone;
}
