package com.transport.transport.service;

import com.transport.transport.model.entity.FreeSeat;
import com.transport.transport.model.request.booking.BookingRequest;

import java.util.List;

public interface SeatService extends CRUDService<FreeSeat> {
    List<FreeSeat> findAllSeatByVehicle(Long vehicleId);
}
