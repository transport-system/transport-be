package com.transport.transport.scheduling;

import com.transport.transport.common.Status;
import com.transport.transport.model.entity.Booking;
import com.transport.transport.model.entity.Trip;
import com.transport.transport.model.entity.Vehicle;
import com.transport.transport.repository.BookingRepository;
import com.transport.transport.repository.TripRepository;
import com.transport.transport.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import javax.transaction.Transactional;

@Component
@RequiredArgsConstructor
@EnableAsync
public class SchedulingTasks {

    private final TripRepository tripRepo;
    VehicleRepository vehicleRepository;
    private final BookingRepository bookingRepository;

    @Async
    @Transactional
    @Scheduled(fixedDelay = 60000)
    public void autoUpdateTrip() {
        List<Trip> tripCheck = tripRepo.findAll();
        for (Trip trip : tripCheck) {
            if(trip.getStatus().equalsIgnoreCase(Status.Trip.INACTIVE.name())){
                break;
            }
            if(trip.getStatus().equalsIgnoreCase(Status.Trip.UPDATE.name())){
                break;
            }
            Timestamp now = Timestamp.from(Instant.now());
            if (trip.getTimeDeparture().after(now) && trip.getTimeArrival().before(now)) {
                trip.setStatus(Status.Trip.DOING.name());
                tripRepo.save(trip);
            }
            if (trip.getTimeArrival().after(now)) {
                trip.setStatus(Status.Trip.INACTIVE.name());
                tripRepo.save(trip);
                //Change status when trip is done
                Vehicle vehicle = trip.getVehicle();
                vehicle.setStatus(Status.Vehicle.ACTIVE.name());
                vehicleRepository.save(vehicle);
            }
            if(trip.getVehicle().getSeatCapacity() <= 0) {
                Vehicle vehicle = trip.getVehicle();
                trip.setStatus(Status.Trip.INACTIVE.name());
                tripRepo.save(trip);
                vehicleRepository.save(vehicle);
            }

        }
        List<Booking> bookingcheck = bookingRepository.findAllByStatus(Status.Booking.PAYLATER.name());
        for(Booking booking: bookingcheck){
            if(booking.getTrip().getTimeDeparture().after(new Timestamp(System.currentTimeMillis()))){
                booking.setStatus(Status.Booking.REFUNDED.name());
                bookingRepository.save(booking);
            }
        }
    }
}
