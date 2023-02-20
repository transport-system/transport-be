package com.transport.transport.mapper;

import com.transport.transport.model.entity.Trip;
import com.transport.transport.model.response.trip.TripResponse;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", config = ConfigurationMapper.class)
public interface TripMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "seatQuantity", target = "seatQuantity")
    TripResponse mapTripResponseFromTrip(Trip trip);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @InheritConfiguration(name = "mapTripResponseFromTrip")
    public List<TripResponse> mapToTripResponse(List<Trip> trip);
}