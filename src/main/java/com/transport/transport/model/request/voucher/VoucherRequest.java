package com.transport.transport.model.request.voucher;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoucherRequest {
    private String voucherCode;
    private Timestamp expiredTime;
    private Integer quantity;
    private BigDecimal discountValue;
}
