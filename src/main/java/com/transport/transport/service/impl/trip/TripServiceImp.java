package com.transport.transport.service.impl.trip;

import com.transport.transport.common.Status;
import com.transport.transport.model.entity.Company;
import com.transport.transport.model.entity.Route;
import com.transport.transport.model.entity.Trip;
import com.transport.transport.model.entity.Vehicle;
import com.transport.transport.model.request.trip.TripRequest;
import com.transport.transport.model.request.trip.UpdateTrip;
import com.transport.transport.repository.CompanyRepository;
import com.transport.transport.repository.RouteRepository;
import com.transport.transport.repository.TripRepository;
import com.transport.transport.repository.VehicleRepository;
import com.transport.transport.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Calendar;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TripServiceImp implements TripService {
    public java.sql.Date date() {
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 1);
        Date TimeDeparture = c.getTime();
        java.sql.Date sqlDate = new java.sql.Date(TimeDeparture.getTime());
        return sqlDate;
    }

    private final TripRepository tripRepo;
    private final RouteRepository routeRepository;
    private final CompanyRepository companyRepository;
    private final VehicleRepository vehicleRepository;

    @Override
    public List<Trip> getAllTrip() {
        return tripRepo.findAll();
    }

    @Override
    public List<Trip> findAll() {
        return null;
    }


    @Override
    public Trip findById(Long id) {
        return tripRepo.findById(id).get();
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
        if (tripRepo.findByStatus(status).isEmpty()) {
            throw new RuntimeException("Status not Exist");
        } else if (statusList.equals(status.equalsIgnoreCase("active"))) {
            return tripRepo.getTripsByStatus(Status.Trip.ACTIVE.name());
        } else if (statusList.equals(status.equalsIgnoreCase("EXPIRED"))) {
            return tripRepo.getTripsByStatus(Status.Trip.EXPIRED.name());
        }
        return tripRepo.getTripsByStatus(status);
    }

    @Override
    public List<Trip> sortTripByTimeArrival() {
        return tripRepo.findAll(Sort.by(Sort.Direction.ASC, "TimeArrival"));
    }

    @Override
    public Trip createrTrip(TripRequest trip) {
        Trip newTrip = new Trip();
        java.sql.Date TimeDeparture = date();
        newTrip.setEmployeeName(trip.getEmployeeName());
        newTrip.setPrice(trip.getPrice());
        newTrip.setImage(trip.getImage());
        newTrip.setDescription(trip.getDescription());
        if (trip.getTimeArrival().after(trip.getTimeReturn())) {
            throw new RuntimeException("TIME ERROR");
        }
        newTrip.setTimeDeparture(TimeDeparture);
        newTrip.setTimeArrival(trip.getTimeArrival());
        newTrip.setTimeReturn(trip.getTimeReturn());
        newTrip.setStatus(Status.Trip.ACTIVE.name());
        Route route = routeRepository.findById(trip.getRouteId()).get();
        newTrip.setRoute(route);
        Company company = companyRepository.findById(trip.getCompanyId()).get();
        newTrip.setCompany(company);
        Vehicle vehicle = vehicleRepository.findById(trip.getVehicleId()).get();
        if (vehicle.getCompany().getId().equals(company.getId())) {
            newTrip.setVehicle(vehicle);
            newTrip.setMaxSeat(vehicle.getTotalSeat());

        } else {
            throw new RuntimeException("Vehicale not exist in Company");
        }
        return tripRepo.save(newTrip);
    }

    @Override
    public Trip updateTrip(UpdateTrip trip, Long Id) {
        Trip tripU = tripRepo.findById(Id).get();
        tripU.setEmployeeName(trip.getEmployeeName());
        tripU.setPrice(trip.getPrice());
        tripU.setImage(trip.getImage());
        tripU.setDescription(trip.getDescription());
        if (trip.getStatus().equalsIgnoreCase("EXPRIED")) {
            tripU.setStatus(Status.Trip.EXPIRED.name());
        }
        if (trip.getStatus().equalsIgnoreCase("ACTIVE")) {
            tripU.setStatus(Status.Trip.ACTIVE.name());
        }
        return tripRepo.save(tripU);
    }
}