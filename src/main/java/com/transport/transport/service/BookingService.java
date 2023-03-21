package com.transport.transport.service;

import com.transport.transport.model.entity.Booking;
import com.transport.transport.model.entity.FreeSeat;
import com.transport.transport.model.request.booking.BookingRequest;
import com.transport.transport.model.request.booking.CancelBooking;
import com.transport.transport.model.request.booking.PaymentRequest;

import java.util.List;

public interface BookingService extends CRUDService<Booking> {

    List<Booking> findAllByAccountId(Long id);
    List<Booking> findAllByCustomerId(Long id);
    List<Booking> findAllByCompany(Long id);
    Booking createBooking(BookingRequest booking);
    Booking payBooking(PaymentRequest method);
    List<FreeSeat> addSeat(List<Integer> numberSeat, Booking booking);
    void refundTicket(Long bookingId);
    void requestRefund (Long bookingId);
    void doneCash(Long bookingId);
}
