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

    //Count
    @Query("SELECT COUNT(t) FROM Trip t WHERE t.status LIKE 'ACTIVE' AND t.company.id = ?1")
    int countTotalVehicleByCompanyId(Long companyId);

    @Query("SELECT COUNT(t) FROM Trip t WHERE t.status LIKE 'ACTIVE' AND t.company.id = ?1")
    int countTotalTripByCompanyId(Long companyId);
}
