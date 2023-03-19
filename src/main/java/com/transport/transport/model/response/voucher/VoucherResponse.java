package com.transport.transport.model.response.voucher;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VoucherResponse {
    private Long id;
    private String voucherCode;
    private String createdTime;
    private String expiredTime;
    private Integer quantity;
    private String status;
    private String discountValue;
}