package com.transport.transport.controller.booking;

import com.transport.transport.common.EndpointConstant;
import com.transport.transport.mapper.BookingMapper;
import com.transport.transport.mapper.SeatMapper;
import com.transport.transport.model.entity.Booking;
import com.transport.transport.model.entity.FreeSeat;
import com.transport.transport.model.request.booking.BookingRequest;
import com.transport.transport.model.response.booking.BookingResponse;
import com.transport.transport.model.response.seat.SeatResponse;
import com.transport.transport.service.BookingService;
import com.transport.transport.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(path = EndpointConstant.Booking.BOOKING_ENDPOINT)
@RequiredArgsConstructor
@RestController
public class BookingController {

    private final BookingService bookingService;
    private final BookingMapper bookingMapper;
    private final SeatService seatService;
    private final SeatMapper seatMapper;

    @PostMapping()
    public ResponseEntity<?> createNewBooking(@RequestBody BookingRequest request) {
        List<FreeSeat> freeSeats = seatService.addSeatIntoVehicle(request);
        List<SeatResponse> responses = seatMapper.mapSeatResponseFromFreeSeat(freeSeats);
        return new ResponseEntity<>(responses, null, 201);
    }

    @GetMapping()
    public ResponseEntity<?> getAllBooking() {
        List<Booking> bookings = bookingService.findAll();
        List<BookingResponse> responses = bookingMapper.createBookingResponseFromBooking(bookings);
        return new ResponseEntity<>(responses, null, 200);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getBookingById(@PathVariable("id") Long id) {
        Booking booking = bookingService.findById(id);
        BookingResponse response = bookingMapper.createBookingResponseFromBooking(booking);
        return new ResponseEntity<>(response, null, 200);
    }
}
