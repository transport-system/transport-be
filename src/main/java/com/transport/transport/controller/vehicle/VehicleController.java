package com.transport.transport.controller.vehicle;

import com.transport.transport.common.EndpointConstant;
import com.transport.transport.mapper.VehicleMapper;
import com.transport.transport.model.entity.Vehicle;
import com.transport.transport.model.request.vehicle.VehicleRequest;

import com.transport.transport.model.response.vehicle.VehicleResponse;
import com.transport.transport.model.response.vehicle.VehicleResponseMsg;
import com.transport.transport.service.VehicleService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = EndpointConstant.Vehicle.VEHICLE_ENDPOINT)
@Api( tags = "Vehicles")
public class VehicleController {
    private final VehicleService vehicleService;
    private final VehicleMapper vehicleMapper;

    @PostMapping("/add")
    public ResponseEntity<?> addVehicle(@RequestBody @Valid VehicleRequest request) {
        Vehicle vehicle = vehicleService.addVehicle(request);
        VehicleResponse res = vehicleMapper.mapToVehicleResponse(vehicle);
        return new ResponseEntity<>(new VehicleResponseMsg("Create Vehicles success", res), null, 200);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVehiclesByStatus(
            @PathVariable(name = "id") Long id) {
        vehicleService.delete(id);
        return new ResponseEntity<>(new VehicleResponseMsg("Status change Inactive Successfully"), null, 200);
    }
//=======================================================================================================================================================================

    @PatchMapping("/company/{id}")
    public ResponseEntity<?> updateStatusActive(
            @PathVariable(name = "id") Long id) {
        Vehicle vehicle = vehicleService.updateStatus(id);
        VehicleResponse response = vehicleMapper.mapToVehicleResponse(vehicle);
        return new ResponseEntity<>(new VehicleResponseMsg("Status change Active Successfully",
                response), null, 200);
    }
    @PatchMapping("/COMPANY/{id}/{companyid}")
    public ResponseEntity<?> updateVehiclesByStatus(
            @PathVariable(name = "id") Long id, @RequestBody VehicleRequest request,@PathVariable(name = "companyid")Long companyid) {
        Vehicle vehicle = vehicleService.updateStatusInActivebyCompanyId(id, request,companyid);
        VehicleResponse response = vehicleMapper.mapToVehicleResponse(vehicle);
        return new ResponseEntity<>(new VehicleResponseMsg("Status change Active Successfully",
                response), null, 200);
    }

//===================================================================================================================
    @GetMapping("")
    public ResponseEntity<?> getAll() {
        List<Vehicle> vehicles = vehicleService.findAll();
        List<VehicleResponse> response = vehicleMapper.mapToVehicleResponse(vehicles);
        return new ResponseEntity<>(new VehicleResponseMsg("Get All Successfully", response), null, 200);
    }
    @GetMapping("/name/{name}")
    public ResponseEntity<?> findVehiclesByTypeName(
            @PathVariable(name = "name") String name) {
        List<Vehicle> vehicles = vehicleService.findVehiclesByName(name);
        List<VehicleResponse> response = vehicleMapper.mapToVehicleResponse(vehicles);
        return new ResponseEntity<>(new VehicleResponseMsg("Get Vehicle by name Successfully", name, response), null, 200);
    }
    @GetMapping("/status/{status}")
    public ResponseEntity<?> findVehiclesByStatus(
            @PathVariable(name = "status") String status) {
        List<Vehicle> vehicles = vehicleService.getVehiclesByStatus(status);
        List<VehicleResponse> response = vehicleMapper.mapToVehicleResponse(vehicles);
        return new ResponseEntity<>(new VehicleResponseMsg("Get Vehicle by Status Successfully",status,response), null, 200);
    }
}
