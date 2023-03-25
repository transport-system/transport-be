package com.transport.transport.repository;

import com.transport.transport.model.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories

public interface TripRepository extends JpaRepository<Trip, Long> {
    @Query("SELECT trip.route.id FROM Trip trip GROUP BY trip.route.id ORDER BY COUNT(trip.route.id) DESC")
    List<Long> findCountAndNameOrderByCountDesc();

    Trip findAllByCompanyIdAndId(Long companyId, Long id);
    Trip findByVehicleId(Long id);
    List<Trip> findByTimeArrival(Date date);
    List<Trip> getTripsByStatus(String status);
    List<Trip> findAllByCompanyId(Long id);
    List<Trip> findAllByCompanyIdAndTimeArrival(Long companyId, Timestamp date);
    List<Trip> findAllByCompanyIdAndStatus(Long companyId, String status);


    //=======ADMIN=======
    @Query("SELECT COUNT(t) FROM Trip t WHERE t.status LIKE 'ACTIVE'")
    int countTotalTrip();

    @Query("SELECT COUNT(t) FROM Trip t WHERE t.status LIKE 'ACTIVE' AND t.timeArrival BETWEEN ?1 AND ?2")
    int countTotalTrip(Timestamp from, Timestamp to);

   // @Query("SELECT COUNT(t) FROM Trip t WHERE t.status LIKE 'ACTIVE' AND t.company.id = ?1")
    int countTripsByCompanyIdAndStatus(Long companyId,String status);

    @Query("SELECT COUNT(t) FROM Trip t WHERE t.status LIKE 'ACTIVE' AND t.company.id = ?1 AND t.id = ?2")
    int countTripByCompanyIdAndStatus(Long companyId, Long tripId);

    @Query(value = "SELECT COUNT(t) FROM Trip t WHERE t.status LIKE 'ACTIVE' " +
            "AND t.company.id = ?1 AND t.timeArrival >= DATE(NOW() - INTERVAL 7 DAY)",
            nativeQuery = true)
    int countTotalTripByCompanyIdLast7Days(Long companyId);

    //By Date
    @Query("SELECT COUNT(t) FROM Trip t WHERE t.status LIKE 'ACTIVE' AND t.company.id = ?1 AND t.timeArrival BETWEEN ?2 AND ?3")
    int countTotalTripByCompanyId(Long companyId, Timestamp from, Timestamp to);

    List<Trip> findAllByRoute_City1AndRoute_City2AndTimeDepartureAndPriceAndCompany_RatingAndCompany_CompanyName
            (String arrival, String departure, Timestamp date, double price, double rating, String name);

    List<Trip> findAllByRoute_City1_CityAndRoute_City2_CityAndPriceAndCompany_Rating(String a, String b, double c, double d);

    @Query("SELECT COUNT(t.vehicle) FROM Trip t WHERE t.company.id=?1 AND t.id = ?2 AND t.vehicle.status='Active'")
    int countVehicleByCompanyIdAndTripId(Long id , Long tripId);
}
