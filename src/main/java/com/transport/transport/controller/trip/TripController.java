package com.transport.transport.controller.trip;
import com.transport.transport.common.EndpointConstant;
import com.transport.transport.common.Status;
import com.transport.transport.mapper.TripMapper;
import com.transport.transport.model.entity.Trip;
import com.transport.transport.model.request.trip.TripRequest;
import com.transport.transport.model.request.trip.UpdateTrip;
import com.transport.transport.model.response.trip.TripMsg;
import com.transport.transport.model.response.trip.TripResponse;
import com.transport.transport.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;

@RestController
@RequiredArgsConstructor
@RequestMapping(EndpointConstant.Trip.TRIP_ENDPOINT)
public class TripController {
    private final TripService tripService;
    private final TripMapper tripMapper;

    @GetMapping()
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(tripService.getAllTrip(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(tripService.findById(id), HttpStatus.OK);
    }
    @GetMapping("/{status}")
    public ResponseEntity<?> getByStatus(@PathVariable(name = "status") String status) {
        return new ResponseEntity<>(tripService.findByStatus(status), HttpStatus.OK);
    }
    @GetMapping("/{date}")
    public ResponseEntity<?> getByArrival(@PathVariable(name = "date") String date) throws ParseException {
        Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        return new ResponseEntity<>(tripService.findByTimeArrival(date1), HttpStatus.OK);
    }
    @GetMapping("/sort")
    public ResponseEntity<?> sortByTimeArrival() {
        return new ResponseEntity<>(tripService.sortTripByTimeArrival(), HttpStatus.OK);
    }
    @PostMapping("/create")
    public ResponseEntity<TripMsg> create(@Valid @RequestBody TripRequest request) {
        Trip trip = tripService.createrTrip(request);

        TripResponse response = tripMapper.mapTripResponseFromTrip(trip);
        return new ResponseEntity<>(new TripMsg("create success", response), null, 200);
    }
    @PutMapping("/{id}")
    public ResponseEntity<TripMsg> update(@Valid @RequestBody UpdateTrip request,
                                          @PathVariable(name = "id") Long id) {
        Trip trip = tripService.updateTrip(request, id);
        TripResponse response = tripMapper.mapTripResponseFromTrip(trip);
        return new ResponseEntity<>(new TripMsg("update success", response), null, 200);
    }
}