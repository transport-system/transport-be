package com.transport.transport.mapper;

import com.transport.transport.model.entity.Trip;
import com.transport.transport.model.response.trip.TripResponeOfConpany;
import com.transport.transport.model.response.trip.TripResponse;
import com.transport.transport.model.response.trip.customer.TripForCustomer;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", config = ConfigurationMapper.class)
public interface TripMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "seatQuantity", target = "seatQuantity")
    @Mapping(source = "id", target = "tripId")
    @Mapping(source = "company.id", target = "companyId")
    @Mapping(source = "vehicle.id", target = "vehicleId")
    @Mapping(source = "route.city1.city", target = "arrival")
    @Mapping(source = "route.city2.city", target = "departure")
    TripResponse mapTripResponseFromTrip(Trip trip);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @InheritConfiguration(name = "mapTripResponseFromTrip")
    public List<TripResponse> mapToTripResponse(List<Trip> trip);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "seatQuantity", target = "seatQuantity")
    @Mapping(source = "id", target = "tripId")
    @Mapping(source = "route.city1.city", target = "arrival")
    @Mapping(source = "route.city2.city", target = "departure")
    TripResponeOfConpany mapTripResponseFromTripOfCompany(Trip trip);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @InheritConfiguration(name = "mapTripResponseFromTripOfCompany")
    public List<TripResponeOfConpany> mapToTripResponseOfCompany(List<Trip> trip);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "seatQuantity", target = "seatQuantity")
    @Mapping(source = "id", target = "tripId")
    @Mapping(source = "company", target = "company")
    @Mapping(source = "route.city1.city", target = "arrival")
    @Mapping(source = "route.city2.city", target = "departure")
    TripForCustomer mapTripResponseFromTripOfCustomer(Trip trip);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @InheritConfiguration(name = "mapTripResponseFromTripOfCustomer")
    public List<TripForCustomer> mapToTripResponseOfCustomer(List<Trip> trip);
}