package com.transport.transport.service;

import com.transport.transport.model.entity.Booking;
import com.transport.transport.model.entity.FreeSeat;
import com.transport.transport.model.entity.Trip;
import com.transport.transport.model.request.booking.BookingRequest;
import com.transport.transport.model.request.booking.PaymentRequest;
import com.transport.transport.model.request.customer.AddCustomerRequest;

import java.util.List;

public interface BookingService extends CRUDService<Booking> {
    Booking createBooking(BookingRequest booking);
    Booking payBooking(PaymentRequest method);
    List<FreeSeat> addSeat(List<Integer> numberSeat, Booking booking);
}
