package com.transport.transport.service.impl.booking;

import com.transport.transport.common.PaymentType;
import com.transport.transport.common.Status;
import com.transport.transport.config.security.user.Account;
import com.transport.transport.exception.BadRequestException;
import com.transport.transport.exception.NotFoundException;
import com.transport.transport.model.entity.*;
import com.transport.transport.model.request.booking.BookingRequest;
import com.transport.transport.model.request.booking.CancelBooking;
import com.transport.transport.model.request.booking.PaymentRequest;
import com.transport.transport.repository.BookingRepository;
import com.transport.transport.repository.SeatRepository;
import com.transport.transport.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
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
    private final VoucherService voucherService;

    private static final long MILLIS_TO_WAIT = 10 * 30000L;
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
    public List<Booking> findAllByCustomerId(Long id) {
        return bookingRepository.findAllByCustomerId(id);
    }

    @Override
    public List<Booking> findAllByAccountId(Long id) {
        return bookingRepository.findAllByAccountId(id);
    }

    @Override
    public List<Booking> findAllByCompany(Long id) {
        List<Booking> booklist = bookingRepository.findAll();
        List<Booking> list = new ArrayList<>();
        for(Booking booking : booklist){
            if(booking.getTrip().getCompany().getId() == id){
                list.add(booking);
            }
        }
        return list;
    }

    @Override
    public Booking createBooking(BookingRequest booking) {
        Booking newBooking = new Booking();

        newBooking.setNote(booking.getNote());

        if (booking.getAccountId() == null && booking.getEmail() != null) {
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

        if (seatNumber > 5) {
            throw new RuntimeException("Require is less 5" +seatId);
        }

        if (seatNumber == 0) {
            throw new RuntimeException("Require is more 0" +seatId);
        }
//      Calculate price
        double totalPrice = newBooking.getTrip().getPrice() * seatNumber;
        Long voucherID = booking.getVoucherId();
        if (voucherID != null) {
            Voucher voucher = voucherService.findById(booking.getVoucherId());
            if (voucher != null) {
                double discount = (totalPrice * Double.parseDouble(
                        String.valueOf(voucher.getDiscountValue()))) / 100;
                totalPrice = totalPrice - discount;
                voucher.setQuantity(voucher.getQuantity() - 1);
                newBooking.setTotalPrice(BigDecimal.valueOf(totalPrice));
            }
        } else {
            newBooking.setTotalPrice(BigDecimal.valueOf(totalPrice));
        }

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
        PaymentRequest method = new PaymentRequest();
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
        return future.isDone() ? payBooking(method) : after;
    }


    @Override
    public Booking payBooking(PaymentRequest method) {
        return bookingRepository.findById(method.getBookingId()).map((booking) -> {
            if (booking.getStatus().equalsIgnoreCase(Status.Booking.PENDING.name())) {
                booking.setStatus(Status.Booking.DONE.name());
                if (method.getMethod().equalsIgnoreCase(PaymentType.CARD.name())
                        || method.getMethod().equalsIgnoreCase(PaymentType.CASH.name())) {
                    booking.setPaymentMethod(method.getMethod().toUpperCase());
                } else {
                    throw new BadRequestException("Payment method is not valid");
                }
                booking.getFreeSeats().forEach((seat) -> {
                    seat.setStatus(Status.Seat.INACTIVE.name());
                });
                flag++;
                return bookingRepository.save(booking);
            } else {
                throw new BadRequestException("Can't pay booking");
            }
        }).orElseThrow(() -> new NotFoundException("Booking id not found: " + method.getBookingId()));
    }

    private void reset(Booking newBooking, Trip trip, Vehicle vehicle, int seatNumber,
                       List<FreeSeat> freeSeats) {

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
                throw new BadRequestException("Seat is pending: " + numberSeat);
            } else if (freeSeat.getStatus().equalsIgnoreCase(Status.Seat.INACTIVE.name())) {
                throw new BadRequestException("Seat is booked: " + numberSeat);
            } else {
                freeSeat.setStatus(Status.Seat.PENDING.name());
                freeSeat.setVehicle(trip.getVehicle());
                freeSeat.setBooking(booking);
                freeSeats.add(freeSeat);
            }
        });
        return freeSeats;
    }


    @Override
    public void ReturnTicket(CancelBooking cancelBooking) {

        List<Integer> numberSeat = cancelBooking.getSeatNumber();
        for(Integer seat: numberSeat){
            FreeSeat freeSeat = seatRepository.findByBooking_IdAndSeatNumber(seat.intValue(), cancelBooking.getBookingId());
            freeSeat.setStatus(Status.Seat.INACTIVE.name());
            seatRepository.save(freeSeat);
        }
        Booking booking = bookingRepository.findById(cancelBooking.getBookingId()).get();

        //Change number seat
        int newNumberOfSeat = booking.getNumberOfSeats() - numberSeat.size();
        booking.setNumberOfSeats(newNumberOfSeat);

        //Check Time
        Calendar c = Calendar.getInstance();
        c.setTime(booking.getCreateBookingTime());
        c.add(Calendar.DATE, +1);
        Timestamp timeAccept = new Timestamp(c.getTimeInMillis());
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Timestamp returnTime = booking.getTrip().getTimeReturn();

        //Check Date to Change total price
        //Check TH1: Time in Create after 1 day
        double price = booking.getTotalPrice().doubleValue() / booking.getNumberOfSeats();
        double newPrice = 0;
        if(now.after(timeAccept)) {
            newPrice = booking.getTotalPrice().doubleValue() - price * numberSeat.size();
        } else if (now.before(returnTime) && now.after(timeAccept)) {
            double total = booking.getTotalPrice().doubleValue() - price * numberSeat.size();
            newPrice = total - (total*(20/100));
        }else {
            newPrice = booking.getTotalPrice().doubleValue();
        }
        booking.setTotalPrice(BigDecimal.valueOf(newPrice));
        //Check if number seat = 0 => This Booking cancle => Change Status
        if (newNumberOfSeat == 0){
            booking.setStatus(Status.Seat.INACTIVE.name());
        }
    }
}
