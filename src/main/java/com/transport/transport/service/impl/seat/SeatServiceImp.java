package com.transport.transport.service.impl.seat;

import com.transport.transport.common.Status;
import com.transport.transport.model.entity.Booking;
import com.transport.transport.model.entity.FreeSeat;
import com.transport.transport.model.entity.Trip;
import com.transport.transport.model.entity.Vehicle;
import com.transport.transport.model.request.booking.BookingRequest;
import com.transport.transport.repository.SeatRepository;
import com.transport.transport.service.BookingService;
import com.transport.transport.service.SeatService;
import com.transport.transport.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatServiceImp implements SeatService {
    private final SeatRepository repository;
    private final TripService tripService;
    private final BookingService bookingService;
    @Override
    public List<FreeSeat> findAll() {
        return null;
    }

    @Override
    public FreeSeat findById(Long id) {
        return null;
    }

    @Override
    public void save(FreeSeat entity) {
        repository.save(entity);
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void update(FreeSeat entity) {

    }

    @Override
    public List<FreeSeat> addSeatIntoVehicle(BookingRequest request) {
        List<FreeSeat> freeSeats = new ArrayList<>();
        Booking booking = bookingService.createBooking(request);
        Trip trip = tripService.findById(request.getTripId());
        Vehicle vehicle = trip.getVehicle();
        List<Integer> seatId = request.getSeatNumber();
        //Seat number of seat
        int seatNumber = seatId.size();
        booking.setNumberOfSeats(seatNumber);

        //Set price
        double totalPrice = booking.getTrip().getPrice() * seatNumber;
        booking.setTotalPrice(BigDecimal.valueOf(totalPrice));

        //Auto set capacity vehicle
        int capacity = vehicle.getSeatCapacity() - seatNumber;
        vehicle.setSeatCapacity(capacity);

        //auto set Status trip and vehicle
        if (capacity == 0) {
            trip.setStatus(Status.Trip.INACTIVE.name());
        }

        //đổi ngược logic lại xem sao
//        seatId.forEach(id -> {
//            FreeSeat freeSeat = repository.findByVehicleIdAndSeatNumber(id, trip.getId());
//            if (freeSeat.getStatus().equalsIgnoreCase(Status.Seat.ACTIVE.name())) {
//                freeSeat.setVehicle(vehicle);
//                freeSeat.setBooking(booking);
//                freeSeat.setStatus(Status.Seat.PENDING.name());
//                repository.save(freeSeat);
//                freeSeats.add(freeSeat);
//            }else if(freeSeat.getStatus().equalsIgnoreCase(Status.Seat.PENDING.name())
//              || freeSeat.getStatus().equalsIgnoreCase(Status.Seat.INACTIVE.name())){
//                throw new RuntimeException("Seat is already booked: " + id);
//            }
//        });
        return freeSeats;
    }
}
