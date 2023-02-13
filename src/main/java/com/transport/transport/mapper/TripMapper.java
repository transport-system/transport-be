package com.transport.transport.mapper;

import com.transport.transport.model.entity.Trip;
import com.transport.transport.model.response.trip.TripResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", config = ConfigurationMapper.class)
public interface TripMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    TripResponse mapTripResponseFromTrip(Trip trip);
}