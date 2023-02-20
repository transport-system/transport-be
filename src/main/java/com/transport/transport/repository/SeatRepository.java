package com.transport.transport.repository;

import com.transport.transport.model.entity.FreeSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<FreeSeat, Long> {



    @Query("SELECT s FROM FreeSeat s WHERE s.seatNumber = ?1 and s.vehicle.id = ?2 ")
    FreeSeat findByVehicleIdAndSeatNumber(Integer seatNumber, Long vehicleId);
}
