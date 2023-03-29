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
    private final VehicleRepository vehicleRepository;
    private final BookingRepository bookingRepository;

    @Async
    @Transactional
    @Scheduled(fixedDelay = 60000)
    public void autoUpdateTrip() {
        List<Trip> tripCheck = tripRepo.findAll();
        for (Trip trip : tripCheck) {
            if(trip.getStatus().equalsIgnoreCase(Status.Trip.ACTIVE.name()) || trip.getStatus().equalsIgnoreCase(Status.Trip.DOING.name())) {
                System.out.println(trip.getId());
                Timestamp now = new Timestamp(System.currentTimeMillis());
                if (now.before(trip.getTimeArrival()) && now.after(trip.getTimeDeparture())) {
                    trip.setStatus(Status.Trip.DOING.name());
                    tripRepo.save(trip);
                }
                else if (now.after(trip.getTimeArrival())) {
                    trip.setStatus(Status.Trip.INACTIVE.name());
                    tripRepo.save(trip);
                    //Change status when trip is done
                    Long id = trip.getVehicle().getId();
                    Vehicle vehicle = vehicleRepository.findById(id).get();
                    vehicle.setStatus(Status.Vehicle.ACTIVE.name());
                    vehicleRepository.save(vehicle);
                }
                if (trip.getVehicle().getSeatCapacity() <= 0) {
                    Vehicle vehicle = trip.getVehicle();
                    trip.setStatus(Status.Trip.INACTIVE.name());
                    tripRepo.save(trip);
                    vehicleRepository.save(vehicle);
                }
            }
        }
        List<Booking> bookingcheck = bookingRepository.findAllByStatus(Status.Booking.PAYLATER.name());
        for(Booking booking: bookingcheck){
            System.out.println(booking.getId());
            if(booking.getTrip().getStatus().equals(Status.Trip.DOING.name())){
                booking.setStatus(Status.Booking.REFUNDED.name());
                bookingRepository.save(booking);
            }
        }
        List<Booking> bookingcheck2 = bookingRepository.findAllByStatus(Status.Booking.REQUESTREFUND.name());
        for(Booking booking: bookingcheck2){
            System.out.println(booking.getId());
            if(booking.getTrip().getStatus().equals(Status.Trip.DOING.name())){
                booking.setStatus(Status.Booking.DONE.name());
                bookingRepository.save(booking);
            }
        }
    }
}
