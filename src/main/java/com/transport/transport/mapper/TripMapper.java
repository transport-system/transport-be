package com.transport.transport.mapper;

import com.transport.transport.model.entity.Trip;
import com.transport.transport.model.response.trip.TripResponeOfConpany;
import com.transport.transport.model.response.trip.TripResponse;
import com.transport.transport.model.response.trip.TripResponseForId;
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
    @Mapping(source = "vehicle.vehicleType", target = "vehicleType")
    @Mapping(source = "route.city1.city", target = "arrival")
    @Mapping(source = "route.city2.city", target = "departure")
    @Mapping(source = "vehicle", target = "vehicle")
    @Mapping(source = "allowPayLater", target = "allowPaylater")
    TripResponse mapTripResponseFromTrip(Trip trip);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @InheritConfiguration(name = "mapTripResponseFromTrip")
    public List<TripResponse> mapToTripResponse(List<Trip> trip);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "seatQuantity", target = "seatQuantity")
    @Mapping(source = "id", target = "tripId")
    @Mapping(source = "employeeName", target = "employeeName")
    @Mapping(source = "vehicle.seatCapacity", target = "vehicle.seatCapacity")
    @Mapping(source = "route.city1.city", target = "arrival")
    @Mapping(source = "route.city2.city", target = "departure")
    @Mapping(source = "allowPayLater", target = "allowPaylater")
    TripResponeOfConpany mapTripResponseFromTripOfCompany(Trip trip);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @InheritConfiguration(name = "mapTripResponseFromTripOfCompany")
    public List<TripResponeOfConpany> mapToTripResponseOfCompany(List<Trip> trip);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "seatQuantity", target = "seatQuantity")
    @Mapping(source = "id", target = "tripId")
    @Mapping(source = "company", target = "company")
    @Mapping(source = "vehicle.licensePlates", target = "vehicle.licensePlates")
    @Mapping(source = "vehicle.vehicleType", target = "vehicle.vehicleType")
    @Mapping(source = "route.city1.city", target = "arrival")
    @Mapping(source = "route.city2.city", target = "departure")
    @Mapping(source = "allowPayLater", target = "allowPaylater")
    TripForCustomer mapTripResponseFromTripOfCustomer(Trip trip);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @InheritConfiguration(name = "mapTripResponseFromTripOfCustomer")
    public List<TripForCustomer> mapToTripResponseOfCustomer(List<Trip> trip);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "seatQuantity", target = "seatQuantity")
    @Mapping(source = "id", target = "tripId")
    @Mapping(source = "company.id", target = "companyId")
    @Mapping(source = "company.companyName", target = "companyName")
    @Mapping(source = "company.rating", target = "companyRating")
    @Mapping(source = "company.feedBacks", target = "feadbacks")
    @Mapping(source = "vehicle.id", target = "vehicleId")
    @Mapping(source = "vehicle.vehicleType", target = "vehicleType")
    @Mapping(source = "route.city1.city", target = "arrival")
    @Mapping(source = "route.city2.city", target = "departure")
    @Mapping(source = "allowPayLater", target = "allowPaylater")
    TripResponseForId mapTripResponseIdFromTrip(Trip trip);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @InheritConfiguration(name = "mapTripResponseIdFromTrip")
    public List<TripResponseForId> mapToTripIdResponse(List<Trip> trip);

}