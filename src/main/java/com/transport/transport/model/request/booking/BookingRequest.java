package com.transport.transport.model.request.booking;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookingRequest {

    @NotNull
    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm")
    private Timestamp createBookingTime;

    private String note;

    private Long seatID;

    private Long accountID;
}
