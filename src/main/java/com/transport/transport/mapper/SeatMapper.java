package com.transport.transport.mapper;

import com.transport.transport.model.entity.FreeSeat;
import com.transport.transport.model.response.seat.SeatResponse;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", config = ConfigurationMapper.class)
public interface SeatMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "id", target = "seatId")
    @Mapping(source = "seatNumber", target = "seatNumber")
    @Mapping(source = "status", target = "status")
    SeatResponse mapSeatResponseFromFreeSeat(FreeSeat freeSeat);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @InheritConfiguration(name = "mapSeatResponseFromFreeSeat")
    List<SeatResponse> mapSeatResponseFromFreeSeat(List<FreeSeat> freeSeats);

//    @Mapping(source = "booking.id", target = "bookingId")
//    @Mapping(source = "vehicle.id", target = "vehicleId")
}
