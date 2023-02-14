package com.transport.transport.service.impl.vehicle;

import com.transport.transport.common.Status;
import com.transport.transport.common.VehicleType;
import com.transport.transport.exception.NotFoundException;
import com.transport.transport.mapper.VehicleMapper;
import com.transport.transport.model.entity.Vehicle;
import com.transport.transport.model.request.vehicle.VehicleRequest;
import com.transport.transport.repository.CompanyRepository;
import com.transport.transport.repository.VehicleRepository;
import com.transport.transport.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleServiceImp implements VehicleService {


    private final VehicleRepository repository;
    private final VehicleMapper vehicleMapper;
    private final CompanyRepository companyRepository;

    @Override
    public List<Vehicle> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Vehicle> findAllByCompanyId(Long id) {
        if (!companyRepository.existsById(id)) {
            throw new NotFoundException("Company not found: " + id);
        } else if (repository.findAllByCompany_Id(id).isEmpty()) {
            throw new NotFoundException("Company not have vehicle");
        }
        return repository.findAllByCompany_Id(id);
    }


    @Override
    public Vehicle findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Vehicle not found: " + id));
    }

    @Override
    public void save(Vehicle entity) {
        repository.save(entity);
    }

    @Override
    public void delete(Long id) {
        Vehicle vehicle = findById(id);
        if (vehicle == null) {
            throw new NotFoundException("Vehicle not found: " + id);
        }
        vehicle.setStatus(Status.Vehicle.INACTIVE.name());
        repository.save(vehicle);
    }

    @Override
    public void update(Vehicle entity) {
        repository.save(entity);
    }

    @Override
    public Vehicle addVehicle(VehicleRequest vehicleRequest) {
        Vehicle vehicle = new Vehicle();
        vehicle.setStatus(Status.Vehicle.INACTIVE.name());
        if (repository.existsByLicensePlates(vehicleRequest.getLicensePlates())) {
            throw new NotFoundException("Vehicle license plates already exists: " + vehicleRequest.getLicensePlates());
        } else if (vehicleRequest.getVehicle_type().equalsIgnoreCase("bus")) {
            vehicle.setVehicle_type_name(VehicleType.BUS.name());
            vehicle.setTotalSeat(40);
        } else if (vehicleRequest.getVehicle_type().equalsIgnoreCase("limousine")) {
            vehicle.setVehicle_type_name(VehicleType.LIMOUSINE.name());
            vehicle.setTotalSeat(9);
        } else {
            throw new NotFoundException("Vehicle type not found: " + vehicleRequest.getVehicle_type());
        }
        vehicleMapper.addVehicleFromVehicleRequest(vehicleRequest, vehicle);
        return repository.save(vehicle);
    }

    @Override
    public List<Vehicle> getVehiclesByStatus(String status) {
        List<Vehicle> vehicles = repository.getVehiclesByStatus(status).stream().toList();
        if (repository.getVehiclesByStatus(status).isEmpty()) {
            throw new RuntimeException("Status not Exist");
        } else if (vehicles.equals(status.equalsIgnoreCase("inactive"))) {
            return repository.getVehiclesByStatus(Status.Vehicle.INACTIVE);
        } else if (vehicles.equals(status.equalsIgnoreCase("active"))) {
            return repository.getVehiclesByStatus(Status.Vehicle.ACTIVE);
        }
        return repository.getVehiclesByStatus(status);

    }

    @Override
    public Vehicle updateStatusInActive(Long id, VehicleRequest request) {
        Vehicle vehicle = findById(id);
        vehicle.setStatus(Status.Vehicle.ACTIVE.name());
        repository.save(vehicle);
        return vehicle;
    }


}
