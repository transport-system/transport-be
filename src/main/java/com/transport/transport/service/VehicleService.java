package com.transport.transport.service;

import com.transport.transport.model.entity.Vehicle;
import com.transport.transport.model.request.vehicle.VehicleRequest;

import java.util.List;

public interface VehicleService extends CRUDService<Vehicle> {
    List<Vehicle> findAllByCompanyId(Long id);

    Vehicle addVehicle(VehicleRequest vehicleRequest);

    List<Vehicle> getVehiclesByStatus(String vehicle);


    List<Vehicle> findVehiclesByName(String name);

    Vehicle updateStatus(Long id);

    Vehicle getVehicleIdAndCompanyId(Long companyid, Long id);
    List<Vehicle> getVehiclenameAndCompanyId(String name, Long companyId);
    List<Vehicle> findAllByStatusAndCompany_Id(String status, Long companyId);
}
