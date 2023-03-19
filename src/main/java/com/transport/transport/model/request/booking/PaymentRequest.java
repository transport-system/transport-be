package com.transport.transport.model.request.booking;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
    private Long bookingId;
    private String method;
}
