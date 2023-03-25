package com.transport.transport.model.response.dashboard;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.transport.transport.model.entity.Booking;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompanyResponse {
    private int totalCustomer;
    private int totalTrip;
    private int totalVehicle;
    private int totalBooking;
    private int totalBookingPending;
    private int totalBookingDone;
    private int totalBookingTimeout;
    private int totalBookingRefunded;
    private int totalBookingRejected;
    private int totalBookingRequestRefund;
    private int totalBookingAwaitPayment;
    private int totalBookingPaymentCash;
    private int totalBookingPaymentCard;
    private BigDecimal totalRevenue;
    private int totalVoucherHave;
    private int totalVoucherIsBooked;

}
