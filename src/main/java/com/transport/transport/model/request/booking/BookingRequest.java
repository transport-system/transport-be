package com.transport.transport.model.request.booking;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.transport.transport.common.Status;
import com.transport.transport.model.request.voucher.VoucherRequest;
import com.transport.transport.utils.ConvertUtils;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class BookingRequest implements Serializable {
    private String note;
    private Long accountId;
    private String firstname;
    private String lastname;
    private String phone;
    private String email;
    private Long tripId;
    private Long voucherId;
    private List<Integer> seatNumber;
}
