package com.transport.transport.mapper;

import com.transport.transport.model.entity.Trip;
import com.transport.transport.model.response.trip.TripResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", config = ConfigurationMapper.class)
public interface TripMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "seatQuantity", target = "seatQuantity")
    @Mapping(source = "vehicle", target = "vehicleResponse")
    @Mapping(source = "route.city1.city", target = "routeResponse.cityDeparture")
    @Mapping(source = "route.city2.city", target = "routeResponse.cityArrival")
    TripResponse mapTripResponseFromTrip(Trip trip);
}