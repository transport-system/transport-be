package com.transport.transport.service.impl.booking;

import com.transport.transport.common.Status;
import com.transport.transport.config.security.user.Account;
import com.transport.transport.exception.BadRequestException;
import com.transport.transport.exception.NotFoundException;
import com.transport.transport.model.entity.*;
import com.transport.transport.model.request.booking.BookingRequest;
import com.transport.transport.model.request.booking.PaymentRequest;
import com.transport.transport.model.request.booking.VoucherBookingRequest;
import com.transport.transport.repository.BookingRepository;
import com.transport.transport.repository.SeatRepository;
import com.transport.transport.repository.VehicleRepository;
import com.transport.transport.repository.VoucherRepository;
import com.transport.transport.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

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
    private final VehicleRepository vehicleRepository;
    private List<TimerTask> scheduledTasks = new ArrayList<>();
    private static final long MILLIS_TO_WAIT = 10 * 30000L;
    private static int flag = 0;
    private final VoucherRepository voucherRepository;

    public String checkDate(Timestamp date1) {
        Date date2 = new Date(date1.getTime());
        SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = sm.format(date2);
        return strDate;
    }

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
        for (Booking booking : booklist) {
            if (booking.getTrip().getCompany().getId() == id) {
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
            throw new RuntimeException("Require is less 5" + seatId);
        }

        if (seatNumber == 0) {
            throw new RuntimeException("Require is more 0" + seatId);
        }
//      Calculate price
        double totalPrice = newBooking.getTrip().getPrice() * seatNumber;
        Long voucherID = booking.getVoucherId();
        Voucher voucher = null;
        if (voucherID != null) {
            voucher = voucherService.findById(voucherID);
            if (voucher != null && voucher.getQuantity() > 0) {
                double discount = (totalPrice * Double.parseDouble(
                        String.valueOf(voucher.getDiscountValue()))) / 100;
                totalPrice = totalPrice - discount;
                voucher.setQuantity(voucher.getQuantity() - 1);
                newBooking.setTotalPrice(BigDecimal.valueOf(totalPrice));
            } else if (voucher.getQuantity() == 0) {
                throw new RuntimeException("Voucher is out of stock");
            } else if (voucher.getExpiredTime().equals(System.currentTimeMillis())) {
                throw new RuntimeException("Voucher is expired");
            } else {
                throw new RuntimeException("Voucher is not exist");
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
    public Booking payBooking(PaymentRequest methodd) {
        return bookingRepository.findById(methodd.getBookingId()).map((booking) -> {
            if (booking.getStatus().equalsIgnoreCase(Status.Booking.PENDING.name())) {
                if (booking.getTrip().getSpecialDay().equalsIgnoreCase("TRUE")) {
                    if (methodd.getMethod().equalsIgnoreCase("CASH")) {
                        booking.setStatus(Status.Booking.PAYLATER.name());
                    } else if (methodd.getMethod().equalsIgnoreCase("CARD")) {
                        throw new BadRequestException("Payment method is not valid");
                    }

                } else if (booking.getTrip().getSpecialDay().equalsIgnoreCase("False")) {
                    if (methodd.getMethod().equalsIgnoreCase("CARD")) {
                        booking.setStatus(Status.Booking.DONE.name());
                    } else if (methodd.getMethod().equalsIgnoreCase("CASH")) {
                        booking.setStatus(Status.Booking.PAYLATER.name());
                    } else {
                        throw new BadRequestException("Payment method is not valid");
                    }
                    booking.setPaymentMethod(methodd.getMethod().toUpperCase());
                    booking.getFreeSeats().forEach((seat) -> {
                        seat.setStatus(Status.Seat.INACTIVE.name());
                    });
                    flag++;
                }
                return bookingRepository.save(booking);
            } else {
                throw new BadRequestException("Can't pay booking");
            }
        }).orElseThrow(() -> new NotFoundException("Booking id not found: " + methodd.getBookingId()));
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
    public void refunded(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).get();
        //Nếu trip đã hoàn thành (status là INACTIVE) thì không thể refund
        if (!booking.getTrip().getStatus().equalsIgnoreCase(Status.Trip.INACTIVE.name())) {
            String status = booking.getStatus();
            if (status.equalsIgnoreCase(Status.Booking.REQUESTREFUND.name())) {
                Vehicle vehicle = booking.getTrip().getVehicle();
                //FreeSeat
                List<FreeSeat> numberSeat = booking.getFreeSeats();
                List<FreeSeat> freeSeats = booking.getFreeSeats().stream()
                        .peek(seat -> {
                            seat.setStatus(Status.Seat.ACTIVE.name());
                            seat.setVehicle(vehicle);
                            seat.setBooking(booking);
                        })
                        .collect(Collectors.toList());
                double price = booking.getTotalPrice().doubleValue() / booking.getNumberOfSeats();
                double newPrice = 0;
                newPrice = booking.getTotalPrice().doubleValue() * 0.1;
                //Vehical
                int capacity = vehicle.getSeatCapacity() - numberSeat.size();
                vehicle.setSeatCapacity(capacity);
                //Booking
                booking.setTotalPrice(BigDecimal.valueOf(newPrice));
                booking.setStatus(Status.Booking.REFUNDED.name());

                vehicleRepository.save(vehicle);
                seatRepository.saveAll(freeSeats);
                bookingRepository.save(booking);
            } else {
                throw new RuntimeException("This ticket is non-refundable");
            }
        } else {
            throw new RuntimeException("Tickets cannot be refunded for past trip");
        }
    }

    @Override
    public void cancelBooing(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).get();
        //Nếu trip đã hoàn thành (status là INACTIVE) thì không thể refund
        if (!booking.getTrip().getStatus().equalsIgnoreCase(Status.Trip.INACTIVE.name())) {
            String status = booking.getStatus();
            if (status.equalsIgnoreCase(Status.Booking.DONE.name()) || status.equalsIgnoreCase(Status.Booking.PAYLATER.name())) {
                Vehicle vehicle = booking.getTrip().getVehicle();
                //FreeSeat
                List<FreeSeat> numberSeat = booking.getFreeSeats();
                List<FreeSeat> freeSeats = booking.getFreeSeats().stream()
                        .peek(seat -> {
                            seat.setStatus(Status.Seat.ACTIVE.name());
                            seat.setVehicle(vehicle);
                            seat.setBooking(booking);
                        })
                        .collect(Collectors.toList());
                double price = booking.getTotalPrice().doubleValue() / booking.getNumberOfSeats();
                double newPrice = 0;
                newPrice = booking.getTotalPrice().doubleValue() * 0.1;
                //Vehical
                int capacity = vehicle.getSeatCapacity() - numberSeat.size();
                vehicle.setSeatCapacity(capacity);
                //Booking
                booking.setTotalPrice(BigDecimal.valueOf(newPrice));
                booking.setStatus(Status.Booking.REJECTED.name());

                vehicleRepository.save(vehicle);
                seatRepository.saveAll(freeSeats);
                bookingRepository.save(booking);
            } else {
                throw new RuntimeException("This ticket is non-refundable");
            }
        } else {
            throw new RuntimeException("Tickets cannot be refunded for past trip");
        }
    }

    @Override
    public void requestRefunded(Long bookingId) {
        Booking change = bookingRepository.findById(bookingId).get();
        Timestamp timeReturn = new Timestamp(change.getTrip().getTimeReturn().getTime());
        Timestamp now = Timestamp.from(Instant.now());
        if (now.before(timeReturn)) { //nếu thời hian hiện tại trước thời gian Return trip cho phép
            if (change.getStatus().equalsIgnoreCase(Status.Booking.DONE.name())) { // chi cho phep khi Đã thanh toán
                change.setStatus(Status.Booking.REQUESTREFUND.name());
                bookingRepository.save(change);
            } else {
                throw new RuntimeException("your ticket has not been paid");
            }
        } else {
            throw new RuntimeException("Your ticket is non-refundable");
        }
    }

    @Override
    public void cancelRequestRefunded(Long bookingId) {
        Booking change = bookingRepository.findById(bookingId).get();
        Timestamp timeReturn = new Timestamp(change.getTrip().getTimeReturn().getTime());
        Timestamp now = Timestamp.from(Instant.now());
        if (now.before(timeReturn)) { //nếu thời hian hiện tại trước thời gian Return trip cho phép
            if (change.getStatus().equalsIgnoreCase(Status.Booking.REQUESTREFUND.name())) { // chi cho phep khi Đã thanh toán
                change.setStatus(Status.Booking.DONE.name());
                bookingRepository.save(change);
            } else {
                throw new RuntimeException("your ticket has not been paid");
            }
        } else {
            throw new RuntimeException("Your ticket is non-refundable");
        }
    }

    @Override
    public void doneCash(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).get();
        if (booking.getStatus().equalsIgnoreCase(Status.Booking.PAYLATER.name())) {
            booking.setStatus(Status.Booking.DONE.name());
            bookingRepository.save(booking);
        } else {
            throw new RuntimeException("Only accept payment for customers using cash payment method !");
        }
    }

    @Override
    public void vouchers(VoucherBookingRequest request) {
        Voucher voucher = voucherService.getVoucherByCode(request.getCode());
        Booking booking = bookingRepository.findById(request.getBookingId()).get();
        double total = booking.getTotalPrice().doubleValue();
        double newTotal;

        if (voucher != null) {
            if (voucher.getCompany() != null) {
                if (!booking.getTrip().getCompany().getId().equals(voucher.getCompany().getId())) {
                    throw new RuntimeException("This voucher can't use with this trip");
                }
            }
            if (voucher.getQuantity() <= 0) {
                throw new RuntimeException("Voucher is out of stock");
            } else if (voucher.getExpiredTime().equals(System.currentTimeMillis())) {
                throw new RuntimeException("Voucher is expired");
            } else if (booking.getVoucher() != null) {
                throw new RuntimeException("Only one voucher for this trip");
            } else {
                double discount = total * 0.2;
                newTotal = total - discount;
                voucher.setQuantity(voucher.getQuantity() - 1);
                booking.setTotalPrice(BigDecimal.valueOf(newTotal));
                booking.setVoucher(voucher);
                voucherRepository.save(voucher);
                bookingRepository.save(booking);
            }
        } else {
            throw new RuntimeException("Voucher is not exist");
        }


    }

}

