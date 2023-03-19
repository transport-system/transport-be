package com.transport.transport.repository;

import com.transport.transport.model.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
@EnableJpaRepositories
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findAllByTrip_Id(Long id);

    List<Booking> findAllByCustomerId(Long id);

    List<Booking> findAllByAccountId(Long id);

    int countAllByTrip_Company_Account_Id(Long id);

    @Query("SELECT COUNT(t.id) FROM Trip t JOIN Booking b WHERE t.id = b.trip.id GROUP BY t.id")
    int countAllByTrip_Id(Long id);

    //Calculate revenue by month
    @Query("SELECT SUM(b.totalPrice) FROM Booking b")
    BigDecimal revenue();

    //Calculate revenue by company
    @Query("SELECT SUM(b.totalPrice) FROM Booking b JOIN Trip t WHERE b.trip.id = t.id GROUP BY t.id")
    BigDecimal revenueByCompany();
}
