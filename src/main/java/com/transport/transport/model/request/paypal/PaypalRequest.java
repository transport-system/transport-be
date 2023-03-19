package com.transport.transport.model.request.paypal;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaypalRequest {
    private Double total;
    private String currency;
}
