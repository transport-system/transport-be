package com.transport.transport.model.response.booking;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.transport.transport.model.entity.Voucher;
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
import java.time.LocalDate;
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
    private String paymentMethod;
    private TripResponse trip;
    private Long examTime;
    private BigDecimal totalPrice;
    private Integer numberOfSeats;
    private String status;

    //Account
    private Long accountID;
    private String username;
    private String firstname;
    private String lastname;
    private String avatarImage;
    private LocalDate dateOfBirth;
    private String email;
    private String phone;
//    private AccountResponse accountResponse;

    //Customer
    private Long c_Id;
    private String c_Firstname;
    private String c_Lastname;
    private String c_Phone;
    private String c_Email;
//    private CustomerResponse customerResponse;

    //Company
    private String companyName;
    private String companyPhone;
    private String companyEmail;


    private TripResponse tripResponse;
    private List<SeatResponse> seatResponse;

    private Voucher voucher;
}
