package com.transport.transport.service.impl.voucher;

import com.transport.transport.common.Status;
import com.transport.transport.exception.NotFoundException;
import com.transport.transport.model.entity.Voucher;
import com.transport.transport.repository.VoucherRepository;
import com.transport.transport.service.VoucherService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VoucherServiceImp implements VoucherService {

    private final VoucherRepository voucherRepository;

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
}
