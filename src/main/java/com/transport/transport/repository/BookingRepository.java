package com.transport.transport.repository;

import com.transport.transport.model.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Repository
@EnableJpaRepositories
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findAllByTrip_Id(Long id);

    List<Booking> findAllByCustomerId(Long id);

    List<Booking> findAllByAccountId(Long id);

    Booking getBookingByVoucher_Id(Long id);

    //=======ADMIN=======
    @Query("SELECT SUM(b.totalPrice) FROM Booking b WHERE b.status = 'DONE'")
    BigDecimal getRevenue();

    @Query("SELECT SUM(b.totalPrice) FROM Booking b WHERE b.status = 'DONE' AND b.createBookingTime BETWEEN ?1 AND ?2")
    BigDecimal getRevenue(Timestamp from, Timestamp to);

    @Query(value = "SELECT MONTH(b.createBookingTime) as month, SUM(b.totalPrice) as revenue FROM Booking b WHERE b.id IN (SELECT t.id FROM Trip t WHERE t.company.id = ?1) AND b.status = 'DONE' GROUP BY MONTH(b.createBookingTime)")
    List<Object[]> getRevenueByMonth(Long id);


    //=======COMPANY=======
    @Query("SELECT COUNT(b) FROM Booking b WHERE b.trip.company.id = ?1")
    int countTotalBookingByCompanyId(Long id);

    @Query(value = "SELECT COUNT(b) FROM Booking b WHERE b.trip.company.id = ?1 AND b.createBookingTime >= DATE(NOW() - INTERVAL 7 DAY)", nativeQuery = true)
    int countTotalBookingByCompanyIdLast7Days(Long id);

    @Query("SELECT COUNT(b) FROM Booking b WHERE b.trip.company.id = ?1 AND b.trip.id = ?2")
    int countTotalBookingByCompanyIdAndTripId(Long companyId, Long tripId);

    @Query("SELECT COUNT(b) FROM Booking b WHERE b.trip.company.id = ?1 AND b.status = ?2")
    int countTotalBookingByCompanyIdAndStatus(Long id, String status);

    @Query("SELECT COUNT(b) FROM Booking b WHERE b.trip.company.id = ?1 AND b.trip.id = ?2 AND b.status = ?3")
    int countTotalBookingByCompanyIdAndTripIdAndStatus(Long companyId, Long tripId, String status);

    @Query(value = "SELECT COUNT(b) FROM Booking b WHERE b.trip.company.id = ?1 AND b.status = ?2  AND b.createBookingTime >= DATE(NOW() - INTERVAL 7 DAY)", nativeQuery = true)
    int countTotalBookingByCompanyIdAndStatusLast7Days(Long id, String status);

    @Query("SELECT SUM(b.totalPrice) FROM Booking b WHERE b.trip.company.id = ?1 AND b.status = 'DONE'")
    BigDecimal getRevenueByCompanyId(Long id);

    @Query(value = "SELECT SUM(b.totalPrice) FROM Booking b WHERE b.trip.company.id = ?1 AND b.status = 'DONE'AND b.createBookingTime >= DATE(NOW() - INTERVAL 7 DAY)", nativeQuery = true)
    BigDecimal getRevenueByCompanyIdLast7Days(Long id);

    @Query("SELECT SUM(b.totalPrice) FROM Booking b WHERE b.trip.company.id = ?1 AND b.trip.id = ?2 AND b.status = 'DONE'")
    BigDecimal getRevenueByCompanyIdAndTripId(Long companyId, Long tripId);

    @Query("SELECT COUNT(b) FROM Booking b WHERE b.trip.company.id = ?1 AND b.createBookingTime BETWEEN ?2 AND ?3")
    int countTotalBookingByCompanyId(Long id, Timestamp from, Timestamp to);

    @Query("SELECT COUNT(b) FROM Booking b WHERE b.trip.company.id = ?1 AND b.status = ?2 AND b.createBookingTime BETWEEN ?3 AND ?4")
    int countTotalBookingByCompanyIdAndStatus(Long id, String status, Timestamp from, Timestamp to);


    @Query("SELECT c.id, COUNT(total_voucher) AS total_voucher FROM Company c JOIN Account a ON c.account.id = a.id JOIN Voucher v on c.id = v.company.id Where c.id = ?1  GROUP BY c.id")
    int countTotalVoucherHaveByCompanyId(Long id);


    @Query("select c.id,Count(b.paymentMethod) from Company c join Booking b on c.account.id=b.account.id where c.id=?1 AND b.paymentMethod =?2 group by c.id")
    int countTotalBookingByTotalPayMenthodwithCompanyID(Long id, String paymentMethod);

    @Query("SELECT c.id,COUNT(b.voucher.id) AS total_vouchers  FROM Booking b join Account a on b.account.id=a.id join Company c on c.account.id=a.id WHERE c.id=?1 group by c.id")
    int countTotalVoucherisBookedByCompanyId(Long id);
}
