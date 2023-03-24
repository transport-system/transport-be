package com.transport.transport.controller.voucher;


import com.transport.transport.common.EndpointConstant;
import com.transport.transport.mapper.VoucherMapper;
import com.transport.transport.model.entity.Voucher;
import com.transport.transport.model.request.voucher.UpdateVoucherRequest;
import com.transport.transport.model.request.voucher.VoucherRequest;
import com.transport.transport.model.response.voucher.VoucherResponse;
import com.transport.transport.model.response.voucher.VoucherResponseMsg;
import com.transport.transport.service.VoucherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = EndpointConstant.Voucher.VOUCHER_ENDPOINT)
@Api( tags = "Vouchers")
public class VoucherController {
    private final VoucherService voucherService;
    private final VoucherMapper voucherMapper;

    @PostMapping("")
    @PreAuthorize("hasAnyAuthority(T(com.transport.transport.common.RoleEnum).ADMIN, T(com.transport.transport.common.RoleEnum).COMPANY) ")
    public ResponseEntity<VoucherResponseMsg> createVoucher(@Valid @RequestBody VoucherRequest req,
                                                            @RequestHeader(AUTHORIZATION) String token) {
        Voucher voucher =  voucherService.createVoucher(req, token);
        return new ResponseEntity<>(new VoucherResponseMsg(
                "Create voucher successfully",
                voucherMapper.createVoucherResponseFromEntity(voucher)), null, HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<VoucherResponseMsg> getAllVoucher() {
        return new ResponseEntity<>(new VoucherResponseMsg(
                "Get all voucher successfully",
                voucherMapper.createVoucherResponseFromEntity(voucherService.findAll())),
                null,
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VoucherResponse> getVoucherById(@PathVariable Long id) {
        return new ResponseEntity<>(
                voucherMapper.createVoucherResponseFromEntity(voucherService.findById(id)),
                null,
                HttpStatus.OK);
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyAuthority(T(com.transport.transport.common.RoleEnum).ADMIN, T(com.transport.transport.common.RoleEnum).COMPANY) ")
    public ResponseEntity<VoucherResponse> updateVoucher(@Valid @RequestBody UpdateVoucherRequest req,
                                                         @RequestHeader(AUTHORIZATION) String token) {
        Voucher voucher = voucherService.updateVoucher(req, token);
        return new ResponseEntity<>(
                voucherMapper.createVoucherResponseFromEntity(voucher),
                null,
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority(T(com.transport.transport.common.RoleEnum).ADMIN)")
    public ResponseEntity<VoucherResponse> deleteVoucher(@PathVariable Long id) {
        Voucher voucher = voucherService.findById(id);
        voucherService.delete(id);
        return new ResponseEntity<>(
                voucherMapper.createVoucherResponseFromEntity(voucher),
                null,
                HttpStatus.OK);
    }

    @ApiOperation(value = "Get voucher by code", response = VoucherResponse.class)
    @GetMapping("/getVoucherByCode/{code}")
    public ResponseEntity<VoucherResponse> getVoucherByCode(@PathVariable String code) {
        return new ResponseEntity<>(
                voucherMapper.createVoucherResponseFromEntity(voucherService.getVoucherByCode(code)),
                null,
                HttpStatus.OK);
    }

    @ApiOperation(value = "Get voucher each role ", response = VoucherResponse.class)
    @GetMapping("/getVoucherByRole/{accountId}")
    public ResponseEntity<VoucherResponseMsg> getVoucherByCompany(@PathVariable Long accountId) {
        return new ResponseEntity<>(
                new VoucherResponseMsg(
                        "Get voucher by company successfully",
                        voucherMapper.createVoucherResponseFromEntity(voucherService.getVouchersByRole(accountId))),
                null,
                HttpStatus.OK);
    }
}
