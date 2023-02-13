package com.transport.transport.service;

;
import com.transport.transport.model.entity.Trip;
import com.transport.transport.model.request.trip.TripRequest;
import com.transport.transport.model.request.trip.UpdateTrip;

import java.util.Date;
import java.util.List;

public interface TripService extends CRUDService<Trip>{

    List<Trip> getAllTrip();
    Trip findById(Long Id);
    List<Trip> findByTimeArrival(Date date);
    Trip createrTrip(TripRequest trip);
    Trip updateTrip(UpdateTrip trip, Long Id);
    List<Trip> findByStatus(String status);

    List<Trip> sortTripByTimeArrival();
}
