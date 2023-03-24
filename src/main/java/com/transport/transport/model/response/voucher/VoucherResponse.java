package com.transport.transport.model.response.voucher;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VoucherResponse implements Serializable {
    private Long id;
    private String voucherCode;
    private String createdTime;
    private String expiredTime;
    private Integer quantity;
    private String status;
    private String discountValue;
    private String owner;
}
