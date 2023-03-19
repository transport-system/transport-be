package com.transport.transport.model.response.dashboard;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

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
    private BigDecimal revenue;
}
