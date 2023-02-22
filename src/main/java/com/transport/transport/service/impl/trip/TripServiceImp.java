package com.transport.transport.service.impl.trip;

import com.transport.transport.common.Status;
import com.transport.transport.exception.NotFoundException;
import com.transport.transport.model.entity.*;
import com.transport.transport.model.request.trip.TripRequest;
import com.transport.transport.model.request.trip.UpdateTrip;
import com.transport.transport.repository.CityRepository;
import com.transport.transport.repository.CompanyRepository;
import com.transport.transport.repository.TripRepository;
import com.transport.transport.repository.VehicleRepository;
import com.transport.transport.service.RouteService;
import com.transport.transport.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TripServiceImp implements TripService {
    private final RouteService routeService;
    private final TripRepository tripRepo;
    private final CompanyRepository companyRepository;
    private final VehicleRepository vehicleRepository;
    private final CityRepository cityRepository;

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
                vehicle.setStatus(Status.Vehicle.ACTIVE.name());
                vehicleRepository.save(vehicle);
            }

            if(trip.getVehicle().getSeatCapacity() <= 0) {
                Vehicle vehicle = trip.getVehicle();
                trip.setStatus(Status.Trip.INACTIVE.name());
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
    public String checkDate(Timestamp date1){
        Date date2 = new Date(date1.getTime());
        SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = sm.format(date2);
        return strDate;
    }


    //Get of User
    @Override
    public List<Trip> findbyArrivalAndDepature(String arrival, String departure,String date){
        List<Trip> listAll = tripRepo.findAll();
        List<Trip> list = new ArrayList<>();
        for(Trip listCheck: listAll){
            if(listCheck.getStatus().equalsIgnoreCase("ACTIVE")) {
                if (listCheck.getRoute().getCity2().getCity().equalsIgnoreCase(departure)
                    && listCheck.getRoute().getCity1().getCity().equalsIgnoreCase(arrival)) {
                    String date1 = checkDate(listCheck.getTimeDeparture());
                    if(date1.equalsIgnoreCase(date)){
                        list.add(listCheck);
                    }
                }
            }
        }
        if(list == null || list.size() == 0){
            throw new RuntimeException("Not Found Trip");
        }
        return list;
    }



    // Get of Admin
    @Override
    public List<Trip> getAllTrip() {
        return tripRepo.findAll();
    }
    @Override
    public Trip findById(Long id) {
        return tripRepo.findById(id).orElseThrow(() -> new NotFoundException("Trip not found: " + id));
    }
    @Override
    public List<Trip> findByTimeArrival(Timestamp date) {
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
    public List<Trip> sortTripByTimeArrival() {
        return tripRepo.findAll(Sort.by(Sort.Direction.ASC, "TimeArrival"));
    }




    //Get of Company
    @Override
    public List<Trip> getAllTripOfCompany(Long companyId) {
        autoUpdateTrip();
        return tripRepo.findAllByCompanyId(companyId);
    }
    @Override
    public Trip findByIdOfCompany(Long companyId, Long Id) {

        return tripRepo.findAllByCompanyIdAndId(companyId, Id);
    }
    @Override
    public List<Trip> findByTimeArrivalOfCompany(Long companyId,Timestamp date) {
        return tripRepo.findAllByCompanyIdAndTimeArrival(companyId, date);
    }
    @Override
    public List<Trip> findByStatusOfCompany(Long companyId,String status) {

        return tripRepo.findAllByCompanyIdAndStatus(companyId, status);
    }
    @Override
    public List<Trip> sortTripByTimeArrivalOfCompany(Long companyId) {
        List<Trip> tripList = tripRepo.findAllByCompanyId(companyId);
        Comparator<Trip> reverseComparator = (c1, c2) -> {
            return c2.getTimeArrival().compareTo(c1.getTimeArrival());
        };
        Collections.sort(tripList, reverseComparator);
        return tripList;
    }



    //-----------------------------------------------------------------
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
        List<Route> route = routeService.allRoute();
        Route checkroute = new Route();
        for (Route route1: route){
            if(route1.getCity1().getCity().equalsIgnoreCase(trip.getCityArrival()) &&
                    route1.getCity2().getCity().equalsIgnoreCase(trip.getCityDeparture())){
                newTrip.setRoute(route1);
                break;
            }
        }
        if(newTrip.getRoute() == null){
            throw new RuntimeException("Not exsit route");
        }


        //set Company
        Company company = companyRepository.findById(trip.getCompanyId()).get();
        newTrip.setCompany(company);

        //set Vehicle
        Vehicle vehicle = vehicleRepository.findById(trip.getVehicleId()).get();
        if (vehicle.getCompany().getId().equals(company.getId())) {
            if (vehicle.getStatus().equalsIgnoreCase("ACTIVE")) {
                newTrip.setVehicle(vehicle);
                newTrip.setSeatQuantity(vehicle.getSeatCapacity());
                //Change status of vehicle
                vehicle.setStatus(Status.Vehicle.INACTIVE.name());
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
    public City addCity(String city){
        City newCity = new City();
        newCity.setCity(city);
        return cityRepository.save(newCity);
    }

    @Override
    public Trip getTripByVehicleId(Long vehicleId) {
        return tripRepo.findByVehicleId(vehicleId).orElseThrow(() -> new NotFoundException("Not have Trip in /" + vehicleId + " /"));
    }
}