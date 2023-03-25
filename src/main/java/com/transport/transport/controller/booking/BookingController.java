package com.transport.transport.controller.booking;

import com.transport.transport.common.EndpointConstant;
import com.transport.transport.mapper.BookingMapper;
import com.transport.transport.model.entity.Booking;
import com.transport.transport.model.request.booking.BookingRequest;
import com.transport.transport.model.request.booking.PaymentRequest;
import com.transport.transport.model.request.booking.VoucherBookingRequest;
import com.transport.transport.model.response.booking.BookingResponse;
import com.transport.transport.service.BookingService;
import com.transport.transport.service.VoucherService;
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

    private final VoucherService voucherService;

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

    @GetMapping("/RefundedBooking/{id}")
    public ResponseEntity<?> refundedBooking(@PathVariable("id") Long id){
        bookingService.refunded(id);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @GetMapping("/RequestRefunded/{id}")
    public ResponseEntity<?> requestRefunded(@PathVariable("id") Long id){
        bookingService.requestRefunded(id);
        return new ResponseEntity<>("Request has been sent", HttpStatus.OK);
    }
    @GetMapping("/CancelRequestRefunded/{id}")
    public ResponseEntity<?> cancelRequestRefunded(@PathVariable("id") Long id){
        bookingService.cancelRequestRefunded(id);
        return new ResponseEntity<>("Request has cancel", HttpStatus.OK);
    }
    @GetMapping("/CancalBooking/{id}")
    public ResponseEntity<?> cancelBooking(@PathVariable("id") Long id){
        bookingService.requestRefunded(id);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
    @GetMapping("/cash/{id}")
    public ResponseEntity<?> cash(@PathVariable("id") Long id){
        bookingService.doneCash(id);
        return new ResponseEntity<>("Payment success", HttpStatus.OK);
    }

    @GetMapping("/voucher")
    public ResponseEntity<?> voucher(@RequestBody VoucherBookingRequest voucherRequest){
        bookingService.voucher(voucherRequest);
        return new ResponseEntity<>("Voucher has Accepted", HttpStatus.OK);
    }
}
