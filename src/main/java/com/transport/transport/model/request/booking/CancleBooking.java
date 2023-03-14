package com.transport.transport.model.request.booking;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CancleBooking {
    private Long bookingId;
    private List<Integer> seatNumber;

}
