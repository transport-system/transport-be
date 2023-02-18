package com.transport.transport.service;
import com.transport.transport.model.entity.Trip;
import com.transport.transport.model.request.trip.TripRequest;
import com.transport.transport.model.request.trip.UpdateTrip;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public interface TripService{

    // Get of admin
    List<Trip> getAllTrip();
    Trip findById(Long Id);
    List<Trip> findByTimeArrival(Timestamp date);
    List<Trip> findByStatus(String status);
    List<Trip> sortTripByTimeArrival();

    //Get of User
    List<Trip> findbyArrivalAndDepature(String arival, String depature);

    //Get of Company
    List<Trip> getAllTripOfCompany(Long companyId);
    Trip findByIdOfCompany(Long companyId, Long Id);
    List<Trip> findByTimeArrivalOfCompany(Long companyId,Timestamp date);
    List<Trip> findByStatusOfCompany(Long companyId,String status);
    Trip changeStatus(Long id, String status);
    List<Trip> sortTripByTimeArrivalOfCompany(Long companyId);

    //--------------------------------------------------------
    Trip createrTrip(TripRequest trip);
    Trip updateTrip(UpdateTrip trip, Long Id);

    Trip findAllByIdAndCompany_IdAndVehicle_Id(Long id, Long coId,Long veId);
    List<Trip> findAllByCompany_IdAndVehicle_Status(Long coId,String veStatus);

}
