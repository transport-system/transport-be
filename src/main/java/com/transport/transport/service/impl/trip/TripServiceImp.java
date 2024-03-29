package com.transport.transport.service.impl.trip;

import com.transport.transport.common.Status;
import com.transport.transport.exception.BadRequestException;
import com.transport.transport.exception.NotFoundException;
import com.transport.transport.model.entity.*;
import com.transport.transport.model.request.trip.TripRequest;
import com.transport.transport.model.request.trip.UpdateTrip;
import com.transport.transport.repository.*;
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
    private final RouteRepository routeRepository;
    private final BookingRepository bookingRepository;

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
    public List<Trip> findbyArrival_DepatureAndTime(String arrival, String departure,String date){
        List<Trip> listAll = tripRepo.findAll();
        List<Trip> list = new ArrayList<>();
        for(Trip listCheck: listAll){
            if(listCheck.getStatus().equalsIgnoreCase(Status.Trip.ACTIVE.name())) {
                if (listCheck.getRoute().getCity2().getCity().equalsIgnoreCase(departure)
                    && listCheck.getRoute().getCity1().getCity().equalsIgnoreCase(arrival)) {
                    if(date != null) {
                        String date1 = checkDate(listCheck.getTimeDeparture());
                        if (date1.equalsIgnoreCase(date)) {
                            list.add(listCheck);
                        }
                    }
                    else{
                        Timestamp dateDeparture = new Timestamp(listCheck.getTimeDeparture().getTime());
                        Timestamp ts = Timestamp.from(Instant.now());
                        if (dateDeparture.after(ts)) {
                            list.add(listCheck);
                        }
                    }
                }
            }
        }
        Collections.sort(list, Comparator.comparing(Trip::getTimeDeparture).reversed());
        if(list == null || list.size() == 0){
            throw new RuntimeException("Not Found Trip");
        }
        return list;
    }
    @Override
    public List<Trip> findbyArrivalAndDepature(String arrival, String departure) {
        List<Trip> listAll = tripRepo.findAll();
        List<Trip> list = new ArrayList<>();
        for(Trip listCheck: listAll){
            if(listCheck.getStatus().equalsIgnoreCase("ACTIVE")) {
                if (listCheck.getRoute().getCity2().getCity().equalsIgnoreCase(departure)
                        && listCheck.getRoute().getCity1().getCity().equalsIgnoreCase(arrival)) {
                        list.add(listCheck);
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
        City departure = cityRepository.findByCity(trip.getCityDeparture());
        City arival = cityRepository.findByCity(trip.getCityArrival());
        Route route = routeRepository.findByCity1_IdAndCity2_Id(arival.getId(), departure.getId());
        newTrip.setRoute(route);
        if(newTrip.getRoute() == null){
            throw new RuntimeException("Not exsit route");
        }

        //set Company
        Company company = companyRepository.findById(trip.getCompanyId())
                .orElseThrow(() -> new NotFoundException("Company not found: " + trip.getCompanyId()));
        newTrip.setCompany(company);

        //Set paylater
        if(trip.getAllowPayLater() == 1){
            newTrip.setAllowPayLater(true);
        }else {
            newTrip.setAllowPayLater(false);
        }

        //set Vehicle
        Vehicle vehicle = vehicleRepository.findById(trip.getVehicleId())
                .orElseThrow(() -> new NotFoundException("vehicle not found: " + trip.getVehicleId()));
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
    public Trip updateTrip(UpdateTrip trip) {
        Trip tripU = tripRepo.findById(trip.getTripId()).get();
        if (!tripU.getStatus().equalsIgnoreCase(Status.Trip.UPDATE.name())) {
            throw new BadRequestException("Cannot update, please disable trip first ");
        } else if(tripU.getTimeArrival().before(new Timestamp(System.currentTimeMillis()))){
            throw new BadRequestException("Cannot update, time arrival is past ");
        } else if (tripU.getStatus().equalsIgnoreCase(Status.Trip.INACTIVE.name())) {
            throw new BadRequestException("Cannot update when trip is inactive ");
        } else if (trip.getTimeDeparture().before(new Timestamp(System.currentTimeMillis()))
        || trip.getTimeArrival().before(new Timestamp(System.currentTimeMillis()))) {
            throw new BadRequestException("Cannot update, because the time is past ");
        }
        tripU.setEmployeeName(trip.getEmployeeName());
        tripU.setPrice(trip.getPrice());
        tripU.setDescription(trip.getDescription());
        if (trip.getTimeDeparture().after(trip.getTimeArrival())
                || trip.getTimeArrival().equals(trip.getTimeDeparture())) {
            throw new RuntimeException("Cannot update, because arrival time is before departure time");
        }
        if(trip.getAllowPaylater() == 1){
            tripU.setAllowPayLater(true);
        }else{
            tripU.setAllowPayLater(false);
        }
        tripU.setTimeDeparture(trip.getTimeDeparture());
        tripU.setTimeArrival(trip.getTimeArrival());
        tripU.setTimeReturn(timeReturn(trip.getTimeDeparture()));

        return tripRepo.save(tripU);
    }

    @Override
    public void deleteTrip(Long id) {
        Trip trip = tripRepo.findById(id).get();
        if (trip.getStatus().equalsIgnoreCase(Status.Trip.INACTIVE.name())) {
            throw new BadRequestException("Cannot delete trip");
        }
        if(bookingRepository.findAllByTrip_Id(id).isEmpty()){
            trip.setStatus(Status.Trip.INACTIVE.name());
            tripRepo.save(trip);
        }
        else{
            throw new RuntimeException("Cannot Inactive this Trip");
        }
    }

    @Override
    public void switchStatusTrip(Long id) {
        Trip trip = tripRepo.findById(id).get();

        /** get a current time **/
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());

        if(trip.getTimeArrival().before(timestamp)){
            throw new BadRequestException("Cannot switch status, time arrival is past ");
        }

        if (trip.getStatus().equalsIgnoreCase(Status.Trip.ACTIVE.name())) {
            trip.setStatus(Status.Trip.UPDATE.name());
            tripRepo.save(trip);
        } else if (trip.getStatus().equalsIgnoreCase(Status.Trip.UPDATE.name())) {
            trip.setStatus(Status.Trip.ACTIVE.name());
            tripRepo.save(trip);
        } else {
            throw new BadRequestException("Trip not Exist");
        }
    }

    @Override
    public City addCity(String city){
        City newCity = new City();
        newCity.setCity(city);
        return cityRepository.save(newCity);
    }

    @Override
    public Trip getTripByVehicleId(Long vehicleId) {
        return tripRepo.findByVehicleId(vehicleId);
    }
}