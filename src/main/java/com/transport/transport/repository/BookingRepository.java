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

    List<Booking> findAllByStatus(String status);

    Booking getBookingByVoucher_Id(Long id);

    //=======ADMIN=======
    @Query("SELECT SUM(b.totalPrice) FROM Booking b WHERE b.status = 'DONE'")
    BigDecimal getRevenue();

    @Query("SELECT SUM(b.totalPrice) FROM Booking b WHERE b.status = 'DONE' AND b.createBookingTime BETWEEN ?1 AND ?2")
    BigDecimal getRevenue(Timestamp from, Timestamp to);

    @Query("SELECT SUM(b.totalPrice) FROM Booking b WHERE b.status = 'DONE' AND b.createBookingTime = ?1")
    BigDecimal getRevenueByDate(String date);

    @Query(value = "SELECT MONTH(b.createBookingTime) as month, SUM(b.totalPrice) as revenue FROM Booking b WHERE b.id IN (SELECT t.id FROM Trip t WHERE t.company.id = ?1) AND b.status = 'DONE' GROUP BY MONTH(b.createBookingTime)")
    List<Object[]> getRevenueByMonth(Long id);


    //=======COMPANY=======

    int countBookingsByAccountCompanyId(Long id);

    @Query(value = "SELECT COUNT(b) FROM Booking b WHERE b.trip.company.id = ?1 AND b.createBookingTime >= DATE(NOW() - INTERVAL 7 DAY)", nativeQuery = true)
    int countTotalBookingByCompanyIdLast7Days(Long id);

    @Query("SELECT COUNT(b) FROM Booking b WHERE b.trip.company.id = ?1 AND b.trip.id = ?2")
    int countBookingByCompanyIdAndTripId(Long companyId, Long tripId);

    int countBookingsByAccountCompanyIdAndStatus(Long id, String status);

    @Query("SELECT count(b) FROM Booking b WHERE b.trip.company.id = ?1 AND b.trip.id = ?2 AND b.status = ?3")
    int countBookingByTripCompanyIdAndTripAndStatus(Long companyId, Long tripId, String status);

    @Query(value = "SELECT COUNT(b) FROM Booking b WHERE b.trip.company.id = ?1 AND b.status = ?2  AND b.createBookingTime >= DATE(NOW() - INTERVAL 7 DAY)", nativeQuery = true)
    int countTotalBookingByCompanyIdAndStatusLast7Days(Long id, String status);

    @Query("SELECT SUM(b.totalPrice) FROM Booking b WHERE b.trip.company.id = ?1 AND b.status = 'DONE'")
    BigDecimal countBookingsByTotalPriceAndaAndAccountCompanyIdAnAndStatus(Long id);

    @Query(value = "SELECT SUM(b.totalPrice) FROM Booking b WHERE b.trip.company.id = ?1 AND b.status = 'DONE'AND b.createBookingTime >= DATE(NOW() - INTERVAL 7 DAY)", nativeQuery = true)
    BigDecimal getRevenueByCompanyIdLast7Days(Long id);

    @Query("SELECT SUM(b.totalPrice) FROM Booking b WHERE b.trip.company.id = ?1 AND b.trip.id = ?2 AND b.status = 'DONE'")
    BigDecimal getRevenueByCompanyIdAndTripId(Long companyId, Long tripId);

    @Query("SELECT COUNT(b) FROM Booking b WHERE b.trip.company.id = ?1 AND b.createBookingTime BETWEEN ?2 AND ?3")
    int countTotalBookingByCompanyId(Long id, Timestamp from, Timestamp to);

    @Query("SELECT COUNT(b) FROM Booking b WHERE b.trip.company.id = ?1 AND b.status = ?2 AND b.createBookingTime BETWEEN ?3 AND ?4")
    int countTotalBookingByCompanyIdAndStatus(Long id, String status, Timestamp from, Timestamp to);

    int countBookingsByAccountCompanyIdAndPaymentMethod(Long id, String paymentMethod);
    @Query("SELECT COUNT(b) FROM Booking b WHERE b.trip.company.id = ?1 AND b.trip.id = ?2 AND b.paymentMethod = ?3")
    int countBookingByTripCompanyIdAndTripIdAndMAndPaymentMethod(Long id, Long tripid, String paymentMethod);


    /** Get last 7 days **/
    @Query(value = "SELECT count(booking_id) as booking_time FROM booking\n" +
            "WHERE date(create_booking_time) >= DATE_ADD(CURDATE(), INTERVAL - 7 DAY)\n" +
            "GROUP BY DATE(create_booking_time) ", nativeQuery = true)
    List<Integer> getAllBookingLast7Days();


    @Query(value = "SELECT count(b.booking_id) as booking_time FROM booking b\n" +
            "INNER JOIN accounts a on a.id = b.account_id " +
            "WHERE date(create_booking_time) >= DATE_ADD(CURDATE(), INTERVAL - 7 DAY) " +
            " b.account_id = ?1\n" +
            "GROUP BY DATE(create_booking_time) ", nativeQuery = true)
    List<Integer> getAllBookingLast7DaysByCompanyId(Long id);


}
