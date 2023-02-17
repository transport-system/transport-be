package com.transport.transport.service;

import com.transport.transport.model.entity.Booking;
import com.transport.transport.model.request.booking.BookingRequest;

public interface BookingService extends CRUDService<Booking> {
    Booking createBooking(BookingRequest booking);

}
