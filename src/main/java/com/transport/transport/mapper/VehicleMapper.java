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
    @Mapping(source = "vehicleType", target = "vehicleType")
    void addVehicleFromVehicleRequest(VehicleRequest vehicleRequest, @MappingTarget Vehicle company);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "vehicleType", target = "vehicleType")
    @Mapping(source = "id", target = "vehicleId")
    @Mapping(source = "seatCapacity", target = "totalSeat")
    @Mapping(source = "company.id", target = "companyId")

    VehicleResponse mapToVehicleResponse(Vehicle vehicle);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @InheritConfiguration(name = "mapToVehicleResponse")
    List<VehicleResponse> mapToVehicleResponse(List<Vehicle> vehicle);
}
