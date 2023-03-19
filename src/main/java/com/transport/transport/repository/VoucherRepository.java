package com.transport.transport.repository;

import com.transport.transport.model.entity.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Long> {
    boolean existsByVoucherCode(String voucherCode);
    Voucher findByVoucherCode(String voucherCode);
}
