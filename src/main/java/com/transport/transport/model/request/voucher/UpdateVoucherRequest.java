package com.transport.transport.model.request.voucher;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateVoucherRequest implements Serializable {
    private Long voucherId;
    private Timestamp startTime;
    private Timestamp expiredTime;
    private Integer quantity;
}

