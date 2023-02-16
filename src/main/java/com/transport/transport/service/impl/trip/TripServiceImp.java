package com.transport.transport.service.impl.trip;

import com.transport.transport.common.Status;
import com.transport.transport.exception.NotFoundException;
import com.transport.transport.model.entity.Company;
import com.transport.transport.model.entity.Route;
import com.transport.transport.model.entity.Trip;
import com.transport.transport.model.entity.Vehicle;
import com.transport.transport.model.request.trip.TripRequest;
import com.transport.transport.model.request.trip.UpdateTrip;
import com.transport.transport.repository.CompanyRepository;
import com.transport.transport.repository.TripRepository;
import com.transport.transport.repository.VehicleRepository;
import com.transport.transport.service.RouteService;
import com.transport.transport.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TripServiceImp implements TripService {
    private final RouteService routeService;
    private final TripRepository tripRepo;
    private final CompanyRepository companyRepository;
    private final VehicleRepository vehicleRepository;

    public void autoUpdateTrip() {
        List<Trip> tripCheck = tripRepo.findAll();
        for (Trip trip : tripCheck) {
            Timestamp now = Timestamp.from(Instant.now());
            if (trip.getTimeDeparture().after(now) || trip.getTimeDeparture().equals(now)) {
                trip.setStatus(Status.Trip.DOING.name());
            }
            if (trip.getTimeArrival().before(now)) {
                trip.setStatus(Status.Trip.INACTIVE.name());
                //Change status when trip is done
                Vehicle vehicle = trip.getVehicle();
                vehicle.setStatus(Status.Vehicle.INACTIVE.name());
                vehicleRepository.save(vehicle);
            }
        }
    }

    public Timestamp timeReturn(Timestamp depatureTime) {
        Calendar c = Calendar.getInstance();
        c.setTime(depatureTime);
        c.add(Calendar.DATE, -1);
        Timestamp returnTime = new Timestamp(c.getTimeInMillis());
        return returnTime;
    }

    @Override
    public List<Trip> getAllTrip() {
        autoUpdateTrip();
        return tripRepo.findAll();
    }

    @Override
    public List<Trip> findAll() {
        return null;
    }


    @Override
    public Trip findById(Long id) {
        return tripRepo.findById(id).orElseThrow(() -> new NotFoundException("Trip not found: " + id));
    }

    @Override
    public void save(Trip entity) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void update(Trip entity) {

    }

    @Override
    public List<Trip> findByTimeArrival(Date date) {
        if (tripRepo.findByTimeArrival(date).isEmpty()) {
            throw new RuntimeException("Not have Trip in /" + date + " /");
        }
        return tripRepo.findByTimeArrival(date);
    }

    @Override
    public List<Trip> findByStatus(String status) {
        List<Trip> statusList = tripRepo.getTripsByStatus(status).stream().toList();
        if (statusList.equals(status.equalsIgnoreCase("DOING"))) {
            return tripRepo.getTripsByStatus(Status.Trip.DOING.name());
        } else if (statusList.equals(status.equalsIgnoreCase("ACTIVE"))) {
            return tripRepo.getTripsByStatus(Status.Trip.ACTIVE.name());
        } else if (statusList.equals(status.equalsIgnoreCase("INACTIVE"))) {
            return tripRepo.getTripsByStatus(Status.Trip.INACTIVE.name());
        } else {
            throw new RuntimeException("Trip not Exist");
        }
    }

    @Override
    public Trip changeStatus(Long id,String status) {
        Trip trip = tripRepo.findById(id).get();
        if (status.equalsIgnoreCase("ACTIVE")) {
            trip.setStatus(Status.Trip.ACTIVE.name());
        }
        if (status.equalsIgnoreCase("INACTIVE")) {
            trip.setStatus(Status.Trip.INACTIVE.name());
        }
        if (status.equalsIgnoreCase("DOING")) {
            trip.setStatus(Status.Trip.DOING.name());
        }
        return trip;
    }

    @Override
    public List<Trip> sortTripByTimeArrival() {
        return tripRepo.findAll(Sort.by(Sort.Direction.ASC, "TimeArrival"));
    }

    @Override
    public Trip createrTrip(TripRequest trip) {
        Trip newTrip = new Trip();
        newTrip.setEmployeeName(trip.getEmployeeName());
        newTrip.setPrice(trip.getPrice());
        newTrip.setImage(trip.getImage());
        newTrip.setDescription(trip.getDescription());
        newTrip.setStatus(Status.Trip.ACTIVE.name());

        //set Time
        if (trip.getTimeDeparture().after(trip.getTimeArrival())
                || trip.getTimeArrival().equals(trip.getTimeDeparture())) {
            throw new RuntimeException("TIME ERROR");
        }
        newTrip.setTimeDeparture(trip.getTimeDeparture());
        newTrip.setTimeArrival(trip.getTimeArrival());
        newTrip.setTimeReturn(timeReturn(trip.getTimeArrival()));

        //Set Route
        if (trip.getCityArrival().equalsIgnoreCase(trip.getCityDeparture())) {
            throw new RuntimeException("City Departure cannot like City Arrival");
        }
        Route route = routeService.create(trip.getCityDeparture(), trip.getCityArrival());
        newTrip.setRoute(route);

        //set Company
        Company company = companyRepository.findById(trip.getCompanyId()).get();
        newTrip.setCompany(company);

        //set Vehicle
        Vehicle vehicle = vehicleRepository.findById(trip.getVehicleId()).get();
        if (vehicle.getCompany().getId().equals(company.getId())) {
            if (vehicle.getStatus().equalsIgnoreCase("ACTIVE")) {
                newTrip.setVehicle(vehicle);
                newTrip.setMaxSeat(vehicle.getTotalSeat());
                //Change status of vehicle
                vehicle.setStatus(Status.Vehicle.ACTIVE.name());
            } else {
                throw new RuntimeException("Vehicle is INACTIVE");
            }
        } else {
            throw new RuntimeException("Vehicle not exist in Company");
        }
        return tripRepo.save(newTrip);
    }

    @Override
    public Trip updateTrip(UpdateTrip trip, Long Id) {
        Trip tripU = tripRepo.findById(Id).get();
        if (!tripU.getStatus().equalsIgnoreCase("ACTIVE")) {
            throw new RuntimeException("Cannot update ");
        }
        tripU.setEmployeeName(trip.getEmployeeName());
        tripU.setPrice(trip.getPrice());
        tripU.setDescription(trip.getDescription());
        if (trip.getTimeDeparture().after(trip.getTimeArrival())
                || trip.getTimeArrival().equals(trip.getTimeDeparture())) {
            throw new RuntimeException("TIME ERROR");
        }
        tripU.setTimeDeparture(trip.getTimeDeparture());
        tripU.setTimeArrival(trip.getTimeArrival());
        tripU.setTimeReturn(timeReturn(trip.getTimeDeparture()));
        autoUpdateTrip();
        return tripRepo.save(tripU);
    }
}