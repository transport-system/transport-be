package com.transport.transport.controller.booking;

import com.transport.transport.common.EndpointConstant;
import com.transport.transport.mapper.BookingMapper;
import com.transport.transport.model.entity.Booking;
import com.transport.transport.model.request.booking.BookingRequest;
import com.transport.transport.model.request.booking.CancelBooking;
import com.transport.transport.model.request.booking.PaymentRequest;
import com.transport.transport.model.response.booking.BookingResponse;
import com.transport.transport.service.BookingService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(path = EndpointConstant.Booking.BOOKING_ENDPOINT)
@RequiredArgsConstructor
@RestController
@Api( tags = "Bookings")
public class BookingController {

    private final BookingService bookingService;
    private final BookingMapper bookingMapper;

    @PreAuthorize("hasAuthority(T(com.transport.transport.common.RoleEnum).ADMIN)")
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

    @GetMapping("/company/{id}")
    public ResponseEntity<?> getBookingByCompany(@PathVariable("id") Long id) {
        List<Booking> bookings = bookingService.findAllByCompany(id);
        List<BookingResponse> response = bookingMapper.createBookingResponseFromBooking(bookings);
        return new ResponseEntity<>(response, null, 200);
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<?> getBookingByCustomer(@PathVariable("id") Long id) {
        List<Booking> bookings = bookingService.findAllByCustomerId(id);
        List<BookingResponse> response = bookingMapper.createBookingResponseFromBooking(bookings);
        return new ResponseEntity<>(response, null, 200);
    }

    @GetMapping("/account/{id}")
    public ResponseEntity<?> getBookingByAccount(@PathVariable("id") Long id) {
        List<Booking> bookings = bookingService.findAllByAccountId(id);
        List<BookingResponse> response = bookingMapper.createBookingResponseFromBooking(bookings);
        return new ResponseEntity<>(response, null, 200);
    }

    @PostMapping()
    public ResponseEntity<?> createNewBooking(@RequestBody BookingRequest request) {
        Booking booking = bookingService.createBooking(request);
        BookingResponse responses = bookingMapper.createBookingResponseFromBooking(booking);
        return new ResponseEntity<>(responses, null, 201);
    }

    @PostMapping("/pay")
    public ResponseEntity<?> payBooking(@RequestBody PaymentRequest payment) {
        Booking booking = bookingService.payBooking(payment);
        BookingResponse responses = bookingMapper.createBookingResponseFromBooking(booking);
        return new ResponseEntity<>(responses, null, 201);
    }

    @GetMapping("/returnTicket")
    public ResponseEntity<?> returnTicket(@RequestParam Long booking){
        bookingService.refundTicket(booking);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @GetMapping("/refund")
    public ResponseEntity<?> requestRefund(@RequestParam Long booking){
        bookingService.requestRefund(booking);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @GetMapping("/cashTicket")
    public ResponseEntity<?> cash(@RequestParam Long booking){
        bookingService.doneCash(booking);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }


}
