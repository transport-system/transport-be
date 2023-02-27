package com.transport.transport.service.impl.booking;

import com.transport.transport.common.Status;
import com.transport.transport.config.security.user.Account;
import com.transport.transport.exception.BadRequestException;
import com.transport.transport.exception.NotFoundException;
import com.transport.transport.model.entity.*;
import com.transport.transport.model.request.booking.BookingRequest;
import com.transport.transport.repository.BookingRepository;
import com.transport.transport.repository.SeatRepository;
import com.transport.transport.service.AccountService;
import com.transport.transport.service.BookingService;
import com.transport.transport.service.CustomerService;
import com.transport.transport.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static java.lang.System.out;

@Service
@RequiredArgsConstructor
public class BookingServiceImp implements BookingService {
    private final BookingRepository bookingRepository;
    private final SeatRepository seatRepository;
    private final AccountService accountService;
    private final TripService tripService;
    private final CustomerService customerService;

    private static final long MILLIS_TO_WAIT = 10 * 1000L;
    private static int flag = 0;
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
        Booking newBooking = new Booking();

        newBooking.setNote(booking.getNote());

        if (booking.getAccountId()==null && booking.getEmail()!=null){
            // Create new customer
            Customer customer = customerService.addCustomer(booking);
            newBooking.setCustomer(customer);
        } else {
            // Get account
            Account account = accountService.findById(booking.getAccountId());
            newBooking.setAccount(account);
        }

        // Get trip
        Trip trip = tripService.findById(booking.getTripId());
        newBooking.setTrip(trip);

        Vehicle vehicle = trip.getVehicle();

        if (trip.getStatus().equalsIgnoreCase(Status.Trip.DOING.name())
                || (trip.getStatus().equalsIgnoreCase(Status.Trip.INACTIVE.name()))) {
            throw new BadRequestException("Can't booking trip");
        }
        newBooking.setStatus(Status.Booking.PENDING.name());

//      Set Time Create
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        newBooking.setCreateBookingTime(timestamp);

//      Calculate exam time
        long examTime = (trip.getTimeArrival().getTime() - trip.getTimeDeparture().getTime()) / 1000 / 3600;
        newBooking.setExamTime(examTime);


//      Seat number of seat
        List<Integer> seatId = booking.getSeatNumber();
        int seatNumber = seatId.size();
        newBooking.setNumberOfSeats(seatNumber);

//      Calculate price
        double totalPrice = newBooking.getTrip().getPrice() * seatNumber;
        newBooking.setTotalPrice(BigDecimal.valueOf(totalPrice));

//      Auto set capacity vehicle
        int capacity = vehicle.getSeatCapacity() - seatNumber;
        vehicle.setSeatCapacity(capacity);

//      Auto set status trip
        if (capacity == 0) {
            trip.setStatus(Status.Trip.INACTIVE.name());
        }

        List<FreeSeat> freeSeats = addSeat(booking.getSeatNumber(), newBooking);
        newBooking.setFreeSeats(freeSeats);
        Booking after = bookingRepository.save(newBooking);

        final ExecutorService executor = Executors.newSingleThreadExecutor();

        // If after 10s, booking is not paid, booking will be rejected
        // else booking will be paid and seat will be inactive
        final Future<?> future = executor.submit(() -> {
            try {
                Thread.sleep(MILLIS_TO_WAIT);
                if (flag == 0) {
                    reset(after, trip, vehicle, seatNumber, freeSeats);
                } else {
                    out.println("Booking is paid");
                    flag = 0;
                }
            } catch (InterruptedException e) {
                out.println("Thread interrupted");
            }
        });
        return future.isDone() ? payBooking(after.getId()) : after;
    }


    @Override
    public Booking payBooking(Long bookingId) {

        return bookingRepository.findById(bookingId).map((booking) -> {
            if (booking.getStatus().equalsIgnoreCase(Status.Booking.PENDING.name())) {
                booking.setStatus(Status.Booking.DONE.name());
                booking.getFreeSeats().forEach((seat) -> {
                    seat.setStatus(Status.Seat.INACTIVE.name());
                });
                flag++;
                return bookingRepository.save(booking);
            } else {
                throw new BadRequestException("Can't pay booking");
            }
        }).orElseThrow(() -> new NotFoundException("Booking id not found: " + bookingId));

    }

    private void reset(Booking newBooking, Trip trip, Vehicle vehicle, int seatNumber, List<FreeSeat> freeSeats) {
        newBooking.setStatus(Status.Booking.REJECTED.name());
        newBooking.setRejectedNote("Time out");
        freeSeats.forEach((seat) -> {
            seat.setStatus(Status.Seat.ACTIVE.name());
            seat.setBooking(null);
        });
        trip.setStatus(Status.Trip.ACTIVE.name());
        vehicle.setSeatCapacity(vehicle.getSeatCapacity() + seatNumber);
        vehicle.setStatus(Status.Vehicle.ACTIVE.name());
        bookingRepository.save(newBooking);
    }

    @Override
    public List<FreeSeat> addSeat(List<Integer> numberSeat, Booking booking) {
        Trip trip = booking.getTrip();
        List<FreeSeat> freeSeats = new ArrayList<>();
        numberSeat.forEach((seat) -> {
                FreeSeat freeSeat = seatRepository.findByVehicleIdAndSeatNumber(seat, trip.getVehicle().getId());
            if (freeSeat.getStatus().equalsIgnoreCase(Status.Seat.PENDING.name())) {
                throw new BadRequestException("Seat is pending: " + freeSeat.getSeatNumber());
            } else if (freeSeat.getStatus().equalsIgnoreCase(Status.Seat.INACTIVE.name())) {
                throw new BadRequestException("Seat is booked: " + freeSeat.getSeatNumber());
            } else {
                freeSeat.setStatus(Status.Seat.PENDING.name());
                freeSeat.setVehicle(trip.getVehicle());
                freeSeat.setBooking(booking);
                freeSeats.add(freeSeat);
            }
        });
        return freeSeats;
    }
}
