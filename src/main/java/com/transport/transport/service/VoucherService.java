package com.transport.transport.service;

import com.transport.transport.model.entity.Voucher;
import com.transport.transport.model.request.voucher.VoucherRequest;

public interface VoucherService extends CRUDService<Voucher> {
    Voucher createVoucher(VoucherRequest voucherRequest);
    Voucher updateVoucher(Long id, VoucherRequest voucherRequest);
    Voucher getVoucherByCode(String code);
}
