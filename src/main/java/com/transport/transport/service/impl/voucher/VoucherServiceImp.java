package com.transport.transport.service.impl.voucher;

import com.transport.transport.common.RoleEnum;
import com.transport.transport.common.Status;
import com.transport.transport.config.security.jwt.JwtService;
import com.transport.transport.config.security.user.Account;
import com.transport.transport.exception.BadRequestException;
import com.transport.transport.exception.NotFoundException;
import com.transport.transport.mapper.VoucherMapper;
import com.transport.transport.model.entity.Booking;
import com.transport.transport.model.entity.Voucher;
import com.transport.transport.model.request.voucher.UpdateVoucherRequest;
import com.transport.transport.model.request.voucher.VoucherRequest;
import com.transport.transport.repository.AccountRepository;
import com.transport.transport.repository.BookingRepository;
import com.transport.transport.repository.VoucherRepository;
import com.transport.transport.service.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VoucherServiceImp implements VoucherService {

    private final VoucherRepository voucherRepository;
    private final AccountRepository accountRepository;
    private final BookingRepository bookingRepository;
    private final JwtService jwtService;
    private final VoucherMapper mapper;

    @PersistenceContext
    private EntityManager entityManager;

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
        if (voucher.getBookings().size() > 0) {
            throw new BadRequestException("Voucher is used");
        }else {
            voucher.setStatus(Status.Voucher.INACTIVE.name());
            voucherRepository.save(voucher);
        };
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
        if (voucherRequest.getQuantity() <= 0) {
            throw new BadRequestException("Quantity must be greater than 0");
        }
        if (voucherRequest.getDiscountValue().intValue() <= 0) {
            throw new BadRequestException("Discount value must be greater than 0");
        }
        if (voucherRequest.getDiscountValue().intValue() >= 0) {
            throw new BadRequestException("Discount value must be lest than 0");
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
        } else if (voucher.getStartTime().before(timestamp)) {
            throw new BadRequestException("Start time must be greater than current time");
        } else if (voucher.getStartTime().after(voucher.getExpiredTime())) {
            throw new BadRequestException("Start time must be less than expired time");
        }
        return voucherRepository.save(voucher);
    }

    @Override
    public Voucher updateVoucher(UpdateVoucherRequest voucherRequest, String token) {
        /** get a account from token **/
        token = token.substring("Bearer ".length());
        String username = jwtService.extractUsername(token);
        Account account = accountRepository.findAccountByUsername(username);
        Booking booking = bookingRepository.getBookingByVoucher_Id(voucherRequest.getVoucherId());
        /** get voucher **/
        Voucher voucher = findById(voucherRequest.getVoucherId());

        /** get a current time **/
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());

        if (voucher.getVoucherCode().equalsIgnoreCase(voucherRequest.getVoucherCode())) {
            throw new BadRequestException("You can not update voucher duplicate code");
        } else if (voucherRequest.getExpiredTime().before(timestamp)) {
            throw new BadRequestException("Expired time must be greater than current time");
        } else if (voucherRequest.getStartTime().before(timestamp)) {
            throw new BadRequestException("Start time must be greater than current time");
        } else if (voucherRequest.getStartTime().after(voucherRequest.getExpiredTime())) {
            throw new BadRequestException("Start time must be less than expired time");
        } else if (voucherRequest.getQuantity() <= 0) {
            throw new BadRequestException("Quantity must be greater than 0");
        } else if (voucherRequest.getDiscountValue().intValue() <= 0) {
            throw new BadRequestException("Discount value must be greater than 0");
        } else if (voucherRequest.getDiscountValue().intValue() >= 100) {
            throw new BadRequestException("Discount value must be less than 100");
        } else if (!voucher.getOwner().equalsIgnoreCase(account.getRole())) {
            throw new BadRequestException("You can not update voucher for other role");
        } else if(voucher.getStatus().equalsIgnoreCase(Status.Voucher.INACTIVE.name())) {
            throw new BadRequestException("You can not update voucher inactive");
        } else if (voucherRequest.getVoucherCode() == null || voucherRequest.getExpiredTime() == null) {
            throw new BadRequestException("You can not update voucher with null value");
        } else if (booking != null) {
            throw new BadRequestException("You can not update voucher has been used");
        }
        else {
            voucher = mapper.createVoucherFromUpdateVoucherRequest(voucherRequest);
        }
        return voucherRepository.save(voucher);
    }

    @Override
    public Voucher getVoucherByCode(String code) {
        return voucherRepository.findByVoucherCode(code);
    }

    @Override
    public List<Voucher> getVouchersByRole(Long accountId) {
        Account account = accountRepository.findById(accountId).get();
        if (account == null) {
            throw new NotFoundException("Account not found: " + accountId);
        } else {
            if (account.getRole().equalsIgnoreCase(RoleEnum.ADMIN.name())) {
                return voucherRepository.getVouchersByOwner(RoleEnum.ADMIN.name());
            } else if (account.getRole().equalsIgnoreCase(RoleEnum.COMPANY.name())) {
                return voucherRepository.getVouchersByOwner(RoleEnum.COMPANY.name());
            } else {
                throw new BadRequestException("User can not get voucher");
            }
        }
    }

    @Override
    public List<Voucher> getAllVouchersOfAccount(Long accountId) {
        accountRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundException("Account not found: " + accountId));
        return voucherRepository.getAllVouchersByAccount(accountId);
    }

    @Override
    public void accountTakeAndSaveVoucher(String token, Long voucherId) {
        token = token.substring("Bearer ".length());
        String username = jwtService.extractUsername(token);
        Account account = accountRepository.findAccountByUsername(username);

        Voucher voucher = voucherRepository.findById(voucherId)
                .orElseThrow(() -> new BadRequestException("Voucher not found: " + voucherId));

        if (voucher.getQuantity() <= 0) {
            throw new BadRequestException("Voucher is out of stock");
        }
        if (voucher.getStartTime().after(new Date())) {
            throw new BadRequestException("Voucher is not started");
        }
        if (voucher.getExpiredTime().before(new Date())) {
            throw new BadRequestException("Voucher is expired");
        }
        if (voucher.getStatus().equalsIgnoreCase(Status.Voucher.INACTIVE.name())) {
            throw new BadRequestException("Voucher is inactive");
        }
        if (voucher.getAccounts().contains(account)) {
            throw new BadRequestException("Account has taken this voucher");
        } else {
            voucher.getAccounts().add(account);
            account.getVouchers().add(voucher);
        }
        accountRepository.save(account);
        voucherRepository.save(voucher);
    }

}
