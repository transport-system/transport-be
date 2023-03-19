package com.transport.transport.model.response.voucher;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VoucherResponseMsg {
    private String message;
    private VoucherResponse data;
    private List<VoucherResponse> dataList;

    public VoucherResponseMsg(String message, VoucherResponse data) {
        this.message = message;
        this.data = data;
    }

    public VoucherResponseMsg(String message, List<VoucherResponse> dataList) {
        this.message = message;
        this.dataList = dataList;
    }
}
