package com.transport.transport.service.impl.vehicle;

import com.transport.transport.common.Status;
import com.transport.transport.common.VehicleType;
import com.transport.transport.exception.NotFoundException;
import com.transport.transport.mapper.VehicleMapper;
import com.transport.transport.model.entity.Company;
import com.transport.transport.model.entity.Vehicle;
import com.transport.transport.model.request.vehicle.VehicleRequest;
import com.transport.transport.repository.CompanyRepository;
import com.transport.transport.repository.VehicleRepository;
import com.transport.transport.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        } else if (repository.findVehicleByCompanyId(id).isEmpty()) {
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
        } else if (vehicleRequest.getVehicleType().equalsIgnoreCase("bus")) {
            vehicle.setVehicleType(VehicleType.BUS.name());
            vehicle.setSeatCapacity(40);
        } else if (vehicleRequest.getVehicleType().equalsIgnoreCase("limousine")) {
            vehicle.setVehicleType(VehicleType.LIMOUSINE.name());
            vehicle.setSeatCapacity(9);
        } else {
            throw new NotFoundException("Vehicle type not found: " + vehicleRequest.getVehicleType());
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
            return repository.getVehiclesByStatus(Status.Vehicle.INACTIVE.name());
        } else if (vehicles.equals(status.equalsIgnoreCase("active"))) {
            return repository.getVehiclesByStatus(Status.Vehicle.ACTIVE.name());
        }
        return repository.getVehiclesByStatus(status);

    }

    @Override
    public Vehicle updateStatusInActivebyCompanyId(Long id, VehicleRequest request, Long companyId) {
        Vehicle vehicle = findById(id);
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new NotFoundException("Company not found: " + companyId));
        vehicle.setStatus(Status.Vehicle.ACTIVE.name());
        repository.save(vehicle);
        return vehicle;
    }

    @Override
    public Vehicle getVehicleIdAndCompanyId(Long companyid, Long id) {

        return repository.findAllByIdAndCompany_Id(companyid, id);
    }

    @Override
    public List<Vehicle> getVehiclenameAndCompanyId(String name, Long companyId) {

        return repository.findAllByVehicleTypeAndCompany_Id(name, companyId);
    }

    @Override
    public List<Vehicle> findAllByStatusAndCompany_Id(String status, Long companyId) {

        return repository.findAllByStatusAndCompany_Id(status, companyId);
    }

    @Override
    public List<Vehicle> findVehiclesByName(String name) {
        List<Vehicle> typeName = repository.findVehicleByVehicle_type_name(name).stream().toList();
        if (repository.findVehicleByVehicle_type_name(name).isEmpty()) {
            throw new RuntimeException("Type name is not Exist");
        } else if (typeName.equals(name.equalsIgnoreCase("bus"))) {
            repository.findVehicleByVehicle_type_name(VehicleType.BUS.name());
        } else if (typeName.equals(name.equalsIgnoreCase("limousine"))) {
            repository.findVehicleByVehicle_type_name(VehicleType.LIMOUSINE.name());

        }
        return repository.findVehicleByVehicle_type_name(name);
    }
}
