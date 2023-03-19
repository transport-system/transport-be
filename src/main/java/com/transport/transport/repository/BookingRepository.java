package com.transport.transport.repository;

import com.transport.transport.model.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.List;

@Repository
@EnableJpaRepositories
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findAllByTrip_Id(Long id);

    List<Booking> findAllByCustomerId(Long id);

    List<Booking> findAllByAccountId(Long id);

    @Query("SELECT COUNT(b) FROM Booking b")
    int countTotalBooking();

    @Query("SELECT COUNT(b) FROM Booking b WHERE b.status = 'REJECTED'")
    int countTotalBookingCancel();

    @Query("SELECT COUNT(b) FROM Booking b WHERE b.status = 'DONE'")
    int countTotalBookingSuccess();

    @Query("SELECT COUNT(b) FROM Booking b WHERE b.status = 'PENDING'")
    int countTotalBookingPending();

    @Query("SELECT COUNT(b) FROM Booking b WHERE b.trip.company.id = ?1")
    int countTotalBookingByCompanyId(Long id);

    @Query("SELECT COUNT(b) FROM Booking b WHERE b.trip.company.id = ?1 AND b.status = ?2")
    int countTotalBookingByCompanyIdAndStatus(Long id, String status);

    @Query("SELECT SUM(b.totalPrice) FROM Booking b WHERE b.status = 'DONE'")
    BigDecimal getRevenue();

    @Query("SELECT SUM(b.totalPrice) FROM Booking b WHERE b.trip.company.id = ?1 AND b.status = 'DONE'")
    BigDecimal getRevenueByCompanyId(Long id);

}
