package com.transport.transport.service;

import com.transport.transport.model.entity.Booking;
import com.transport.transport.model.entity.FreeSeat;
import com.transport.transport.model.request.booking.BookingRequest;
import com.transport.transport.model.request.booking.CancelBooking;
import com.transport.transport.model.request.booking.PaymentRequest;
import com.transport.transport.model.request.booking.VoucherRequest;

import java.util.List;

public interface BookingService extends CRUDService<Booking> {

    List<Booking> findAllByAccountId(Long id);
    List<Booking> findAllByCustomerId(Long id);
    List<Booking> findAllByCompany(Long id);
    Booking createBooking(BookingRequest booking);
    Booking payBooking(PaymentRequest method);
    List<FreeSeat> addSeat(List<Integer> numberSeat, Booking booking);
    public void refunded(Long bookingId);
    public void requestRefunded(Long bookingId);
    public void doneCash(Long bookingId);
    public void voucher(VoucherRequest request);
    public void cancelRequestRefunded(Long bookingId);

}
