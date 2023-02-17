package com.transport.transport.repository;

import com.transport.transport.model.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
    List<Trip> findByTimeArrival(Date date);
    List<Trip> findByStatus(String status);
    List<Trip> getTripsByStatus(String status);
    List<Trip> findAllByCompanyId(Long id);
    Trip findAllByCompanyIdAndId(Long companyId, Long id);
    List<Trip> findAllByCompanyIdAndTimeArrival(Long companyId, Timestamp date);
    List<Trip> findAllByCompanyIdAndStatus(Long companyId, String status);

}
