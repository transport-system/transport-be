package com.transport.transport.repository;

import com.transport.transport.model.entity.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Long> {
    List<Voucher> getVouchersByCompany_Id(Long companyId);
    boolean existsByVoucherCode(String voucherCode);
    Voucher findByVoucherCode(String voucherCode);
}
