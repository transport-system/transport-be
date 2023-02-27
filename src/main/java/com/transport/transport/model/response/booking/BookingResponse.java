package com.transport.transport.model.response.booking;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.transport.transport.model.response.account.AccountResponse;
import com.transport.transport.model.response.customer.CustomerResponse;
import com.transport.transport.model.response.seat.SeatResponse;
import com.transport.transport.model.response.trip.TripResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingResponse {
    private Long id;
    private Timestamp createBookingTime;
    private String note;
    private Long examTime;
    private BigDecimal totalPrice;
    private Integer numberOfSeats;
    private String status;
    private AccountResponse accountResponse;
    private CustomerResponse customerResponse;
    private TripResponse tripResponse;
    private List<SeatResponse> seatResponse;
}
