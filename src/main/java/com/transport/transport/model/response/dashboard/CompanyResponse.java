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
    private int totalBookingCancel;
    private int totalBookingPending;
    private int totalBookingSuccess;
    private BigDecimal totalRevenue;
}
