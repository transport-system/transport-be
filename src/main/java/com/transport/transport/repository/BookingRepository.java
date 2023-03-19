package com.transport.transport.repository;

import com.transport.transport.model.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findAllByTrip_Id(Long id);

    List<Booking> findAllByCustomerId(Long id);

    List<Booking> findAllByAccountId(Long id);

    int countAllByTrip_Company_Account_Id(Long id);

    @Query("SELECT COUNT(Booking.id) FROM Booking b JOIN Trip t WHERE b.trip.id = t.id")
    int countAllByTrip_Id(Long id);

    @Query("SELECT MONTH(Booking.createBookingTime) as month, " +
            "SUM(Booking.totalPrice) as revenue FROM Booking GROUP BY MONTH(Booking.createBookingTime)")
    BigDecimal revenue();

    @Query("SELECT MONTH(Booking.createBookingTime) as m, SUM(Booking.totalPrice) as revenue " +
            "FROM Booking B JOIN Company C WHERE B.trip.company.id = C.id " +
            "GROUP BY MONTH(Booking.createBookingTime)")
    BigDecimal revenueByCompany();
}
