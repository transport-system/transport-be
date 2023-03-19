package com.transport.transport.mapper;

import com.transport.transport.model.entity.Booking;
import com.transport.transport.model.response.booking.BookingResponse;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = "spring", config = ConfigurationMapper.class)

public interface BookingMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "id", target = "id")
    @Mapping(source = "createBookingTime", target = "createBookingTime")
    @Mapping(source = "examTime", target = "examTime")
    @Mapping(source = "totalPrice", target = "totalPrice")
    @Mapping(source = "numberOfSeats", target = "numberOfSeats")
    @Mapping(source = "status", target = "status")
    //Account
    @Mapping(source = "account.id", target = "accountID")
    @Mapping(source = "account.username", target = "username")
    @Mapping(source = "account.firstname", target = "firstname")
    @Mapping(source = "account.lastname", target = "lastname")
    @Mapping(source = "account.email", target = "email")
    @Mapping(source = "account.phone", target = "phone")
    //Customer
    @Mapping(source = "customer.id", target = "c_Id")
    @Mapping(source = "customer.firstname", target = "c_Firstname")
    @Mapping(source = "customer.lastname", target = "c_Lastname")
    @Mapping(source = "customer.phone", target = "c_Phone")
    @Mapping(source = "customer.email", target = "c_Email")
    //Company
    @Mapping(source ="trip.company.companyName", target = "companyName")
    @Mapping(source ="trip.company.account.email", target = "companyEmail")
    @Mapping(source ="trip.company.account.phone", target = "companyPhone")

    @Mapping(source = "trip.id", target = "tripResponse.tripId")
    @Mapping(source = "trip.route.city1.city", target = "tripResponse.departure")
    @Mapping(source = "trip.route.city2.city", target = "tripResponse.arrival")
    @Mapping(target = "seatResponse", source ="freeSeats")
    @Mapping(target = "paymentMethod", source ="paymentMethod")
//    @Mapping(target = "seatResponse.seatId")
    BookingResponse createBookingResponseFromBooking(Booking booking);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @InheritConfiguration(name = "createBookingResponseFromBooking")
    List<BookingResponse> createBookingResponseFromBooking(List<Booking> booking);


}
