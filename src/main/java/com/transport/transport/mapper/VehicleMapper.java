package com.transport.transport.mapper;


import com.transport.transport.model.entity.Vehicle;
import com.transport.transport.model.request.vehicle.VehicleRequest;
import com.transport.transport.model.response.vehicle.VehicleResponse;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", config = ConfigurationMapper.class)
public interface VehicleMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "companyId", target = "company.id")
    @Mapping(source = "vehicle_type", target = "vehicle_type_name")
    void addVehicleFromVehicleRequest(VehicleRequest vehicleRequest, @MappingTarget Vehicle company);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "vehicle_type_name", target = "vehicle_type")
    @Mapping(source = "id", target = "id")
    VehicleResponse mapToVehicleResponse(Vehicle vehicle);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @InheritConfiguration(name = "mapToVehicleResponse")
    List<VehicleResponse> mapToVehicleResponse(List<Vehicle> vehicle);
}
