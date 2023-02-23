package com.transport.transport.service;

import com.transport.transport.model.entity.Booking;
import com.transport.transport.model.entity.FreeSeat;
import com.transport.transport.model.entity.Trip;
import com.transport.transport.model.request.booking.BookingRequest;

import java.util.List;

public interface BookingService extends CRUDService<Booking> {
    Booking createBooking(BookingRequest booking);
    List<FreeSeat> addSeat(List<Integer> numberSeat, Booking booking);
}
