package com.transport.transport.service;

import com.transport.transport.model.entity.Voucher;
import com.transport.transport.model.request.voucher.UpdateVoucherRequest;
import com.transport.transport.model.request.voucher.VoucherRequest;

import java.util.List;

public interface VoucherService extends CRUDService<Voucher> {
    Voucher createVoucher(VoucherRequest voucherRequest, String token);
    Voucher updateVoucher(UpdateVoucherRequest voucherRequest, String token);
    Voucher getVoucherByCode(String code);
    List<Voucher> getVouchersByCompany(Long companyId);
}
