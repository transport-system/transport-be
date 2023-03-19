package com.transport.transport.model.request.booking;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CancelBooking {
    private Long bookingId;
    private List<Integer> seatNumber;

}
