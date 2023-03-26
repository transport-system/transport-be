package com.transport.transport.model.request.booking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoucherBookingRequest implements Serializable {
    private String code;
    private Long bookingId;
}
