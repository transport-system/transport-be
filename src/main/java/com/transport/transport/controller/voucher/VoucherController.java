package com.transport.transport.controller.voucher;


import com.transport.transport.common.EndpointConstant;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = EndpointConstant.Voucher.VOUCHER_ENDPOINT)
@Api( tags = "Vouchers")
public class VoucherController {
}
