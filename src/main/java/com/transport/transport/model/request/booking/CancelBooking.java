package com.transport.transport.model.request.booking;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CancelBooking implements Serializable {
    private Long bookingId;
    private List<Integer> seatNumber;

}
