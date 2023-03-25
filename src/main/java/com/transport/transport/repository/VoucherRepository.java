package com.transport.transport.repository;

import com.transport.transport.model.entity.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Long> {
    List<Voucher> getVouchersByOwner(String owner);
    boolean existsByVoucherCode(String voucherCode);
    Voucher findByVoucherCode(String voucherCode);

<<<<<<< HEAD
=======
    @Query("SELECT v FROM Account a JOIN a.vouchers v WHERE a.id = ?1")
    List<Voucher> getAllVouchersByAccount(Long accountId);
>>>>>>> b8751e417f6309fba3de95df9bf0d81aff01bb22
}
