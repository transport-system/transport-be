package com.transport.transport.model.request.paypal;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaypalRequest {
    Double total;
    String cancelUrl;
    String successUrl;

}
