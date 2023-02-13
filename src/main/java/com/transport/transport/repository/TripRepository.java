package com.transport.transport.repository;

import com.transport.transport.common.Status;
import com.transport.transport.model.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip,Long> {
    List<Trip> findByTimeArrival(Date date);
    List<Trip> findByStatus(String status);
}
