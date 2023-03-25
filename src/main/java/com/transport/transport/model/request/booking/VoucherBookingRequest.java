package com.transport.transport.model.request.booking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoucherBookingRequest {
    private String code;
    private Long bookingId;
}