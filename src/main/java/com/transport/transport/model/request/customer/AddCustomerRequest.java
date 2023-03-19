package com.transport.transport.model.request.customer;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddCustomerRequest {
    private String firstname;
    private String lastname;
    private String phone;
    private String email;
}
