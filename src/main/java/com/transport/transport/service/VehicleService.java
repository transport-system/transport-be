package com.transport.transport.service;

import com.transport.transport.model.entity.Vehicle;
import com.transport.transport.model.request.vehicle.VehicleRequest;

import java.util.List;

public interface VehicleService extends CRUDService<Vehicle> {
    List<Vehicle> findAllByCompanyId(Long id);

    Vehicle addVehicle(VehicleRequest vehicleRequest);

    List<Vehicle> getVehiclesByStatus(String vehicle);
    Vehicle updateStatusInActive(Long id, VehicleRequest request);

    List<Vehicle> findVehiclesByName(String name);
}
