package com.transport.transport.model.request.booking;

import com.transport.transport.utils.ConvertUtils;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookingRequest {
    private String note;
    private Long accountId;
    private Long tripId;
    private List<Integer> seatNumber;
}
