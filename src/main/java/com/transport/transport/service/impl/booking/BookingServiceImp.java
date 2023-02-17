package com.transport.transport.service.impl.booking;

import com.transport.transport.common.Status;
import com.transport.transport.model.entity.Booking;
import com.transport.transport.model.request.booking.BookingRequest;
import com.transport.transport.repository.AccountRepository;
import com.transport.transport.repository.BookingRepository;
import com.transport.transport.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImp implements BookingService {
    private final BookingRepository bookingRepository;
    private final AccountRepository accountRepository;

    @Override
    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    @Override
    public Booking findById(Long id) {
        return bookingRepository.findById(id).orElseThrow(() -> new RuntimeException("Booking id not found: " + id));
    }

    @Override
    public void save(Booking entity) {
        bookingRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        Booking booking = findById(id);
        booking.setStatus(Status.Booking.REJECTED.name());
        bookingRepository.save(booking);
    }

    @Override
    public void update(Booking entity) {
        bookingRepository.save(entity);
    }

    @Override
    public Booking createBooking(BookingRequest booking) {
        return null;
    }
}
