package com.transport.transport.repository;

import com.transport.transport.model.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
    @Query("SELECT DISTINCT trip.route.id FROM Trip trip GROUP BY trip.route ORDER BY COUNT(trip.route.id) DESC")
    List<Long> findCountAndNameOrderByCountDesc();

    Trip findAllByCompanyIdAndId(Long companyId, Long id);
    Trip findByVehicleId(Long id);
    List<Trip> findByTimeArrival(Date date);
    List<Trip> getTripsByStatus(String status);
    List<Trip> findAllByCompanyId(Long id);
    List<Trip> findAllByCompanyIdAndTimeArrival(Long companyId, Timestamp date);
    List<Trip> findAllByCompanyIdAndStatus(Long companyId, String status);


    Trip findAllByIdAndCompany_IdAndVehicle_Id(Long id, Long coId,Long veId);
    List<Trip> findAllByCompany_IdAndVehicle_Status(Long coId,String veStatus);
}
