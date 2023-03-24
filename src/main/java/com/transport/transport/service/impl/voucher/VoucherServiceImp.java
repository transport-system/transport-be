package com.transport.transport.service.impl.voucher;

import com.transport.transport.common.RoleEnum;
import com.transport.transport.common.Status;
import com.transport.transport.config.security.jwt.JwtService;
import com.transport.transport.config.security.user.Account;
import com.transport.transport.exception.BadRequestException;
import com.transport.transport.exception.NotFoundException;
import com.transport.transport.mapper.VoucherMapper;
import com.transport.transport.model.entity.Voucher;
import com.transport.transport.model.request.voucher.UpdateVoucherRequest;
import com.transport.transport.model.request.voucher.VoucherRequest;
import com.transport.transport.repository.AccountRepository;
import com.transport.transport.repository.VoucherRepository;
import com.transport.transport.service.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VoucherServiceImp implements VoucherService {

    private final VoucherRepository voucherRepository;
    private final AccountRepository accountRepository;
    private final JwtService jwtService;
    private final VoucherMapper mapper;

    @Override
    public List<Voucher> findAll() {
        return voucherRepository.findAll();
    }

    @Override
    public Voucher findById(Long id) {
        return voucherRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Voucher not found: " + id));
    }

    @Override
    public void save(Voucher entity) {
        voucherRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        Voucher voucher = findById(id);
        if (voucher == null) {
            throw new NotFoundException("Voucher not found: " + id);
        }
        voucher.setStatus(Status.Voucher.INACTIVE.name());
        voucherRepository.save(voucher);
    }

    @Override
    public void update(Voucher entity) {
        Voucher voucher = findById(entity.getId());
        if (voucher == null) {
            throw new NotFoundException("Voucher not found: " + voucher.getId());
        }

        voucher.setVoucherCode(entity.getVoucherCode());
        voucher.setQuantity(entity.getQuantity());
        voucher.setExpiredTime(entity.getExpiredTime());
        voucher.setDiscountValue(entity.getDiscountValue());
        voucher.setStatus(entity.getStatus());
        voucherRepository.save(voucher);
    }



    @Override
    public Voucher createVoucher(VoucherRequest voucherRequest, String token) {
        Voucher voucher = mapper.createVoucherFromRequest(voucherRequest);
        token = token.substring("Bearer ".length());
        String username = jwtService.extractUsername(token);
        Account account = accountRepository.findAccountByUsername(username);
        if (voucherRepository.existsByVoucherCode(voucherRequest.getVoucherCode())) {
            throw new BadRequestException("You can not create voucher duplicate code");
        }
        //get current time
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        voucher.setStatus(Status.Voucher.ACTIVE.name());
        voucher.setCreatedTime(timestamp);
        Account account1 = accountRepository.existsById(voucherRequest.getOwnerId()) ?
                accountRepository.findById(voucherRequest.getOwnerId()).get() : null;
        if (account1.getRole().equalsIgnoreCase(RoleEnum.COMPANY.name())) {
            voucher.setCompany(account1.getCompany());
            voucher.setOwner(RoleEnum.COMPANY.name());
        } else if (account1.getRole().equalsIgnoreCase(RoleEnum.ADMIN.name())) {
            voucher.setOwner(RoleEnum.ADMIN.name());
        } else if (account1.getRole().equalsIgnoreCase(RoleEnum.USER.name())) {
            throw new BadRequestException("User can not create voucher");
        } else {
            throw new NotFoundException("Account not found: " + voucherRequest.getOwnerId());
        }
        if (!account.getRole().equalsIgnoreCase(account1.getRole())) {
            throw new BadRequestException("Cannot create voucher for other role");
        }
        if (voucher.getExpiredTime().before(timestamp)) {
            throw new BadRequestException("Expired time must be greater than current time");
        }
        return voucherRepository.save(voucher);
    }

    @Override
    public Voucher updateVoucher(UpdateVoucherRequest voucherRequest) {
        Voucher voucher = findById(voucherRequest.getVoucherId());
        voucher = mapper.createVoucherFromUpdateVoucherRequest(voucherRequest);
        return voucherRepository.save(voucher);
    }

    @Override
    public Voucher getVoucherByCode(String code) {
        return voucherRepository.findByVoucherCode(code);
    }
}
