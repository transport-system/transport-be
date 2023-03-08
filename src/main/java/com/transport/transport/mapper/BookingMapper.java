package com.transport.transport.mapper;

import com.transport.transport.model.entity.Booking;
import com.transport.transport.model.response.booking.BookingResponse;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = "spring", config = ConfigurationMapper.class)

public interface BookingMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "id", target = "id", ignore = true)
    @Mapping(source = "createBookingTime", target = "createBookingTime", ignore = true)
    @Mapping(source = "examTime", target = "examTime")
    @Mapping(source = "totalPrice", target = "totalPrice")
    @Mapping(source = "numberOfSeats", target = "numberOfSeats")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "account.id", target = "accountResponse.id")
    @Mapping(source = "trip.id", target = "tripResponse.tripId")
    @Mapping(source = "customer", target = "customerResponse")
    @Mapping(target = "seatResponse", source ="freeSeats")
    @Mapping(target = "paymentMethod", source ="paymentMethod")
//    @Mapping(target = "seatResponse.seatId")
    BookingResponse createBookingResponseFromBooking(Booking booking);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @InheritConfiguration(name = "createBookingResponseFromBooking")
    List<BookingResponse> createBookingResponseFromBooking(List<Booking> booking);


}
