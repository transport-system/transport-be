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


    @Query("SELECT v FROM Account a JOIN a.vouchers v WHERE a.id = ?1")
    List<Voucher> getAllVouchersByAccount(Long accountId);

    int countVoucherByCompanyId(Long id);

    @Query("SELECT COUNT(voucher.id) FROM Booking WHERE account.id = ?1 AND voucher.id IS NOT NULL\n")
    int countVouchersByBookings(Long id);

    @Query("SELECT COUNT(b.voucher.id) FROM Booking b WHERE b.trip.company.id = ?1 AND b.trip.id = ?2 AND b.voucher.id IS NOT NULL\n ")
    int countVouchersByCompanyIdAndTripId(Long id,Long tripid);
}
