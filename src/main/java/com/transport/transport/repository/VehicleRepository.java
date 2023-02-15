package com.transport.transport.repository;

import com.transport.transport.model.entity.Vehicle;
import com.transport.transport.model.request.vehicle.VehicleRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    boolean existsByLicensePlates(String licensePlate);

    boolean existsById(Long id);

    //    @Query("SELECT v FROM Vehicle v WHERE v.company.id = ?1")
    List<Vehicle> findAllByCompany_Id(Long id);

    List<Vehicle> findVehicleByCompanyId(Long id);


    List<Vehicle> getVehiclesByStatus(String vehicle);

    @Query("SELECT v FROM Vehicle v WHERE v.vehicle_type_name LIKE %?1%")
    List<Vehicle> findVehicleByVehicle_type_name(String name);
}

}
