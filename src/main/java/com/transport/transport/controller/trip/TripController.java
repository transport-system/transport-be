package com.transport.transport.controller.trip;
import com.transport.transport.common.EndpointConstant;
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
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;

@RestController
@RequiredArgsConstructor
@RequestMapping(EndpointConstant.Trip.TRIP_ENDPOINT)
public class TripController {
    private final TripService tripService;
    private final TripMapper tripMapper;

    //Trip Of Admin
    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(tripService.getAllTrip(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(tripService.findById(id), HttpStatus.OK);
    }
    @GetMapping("status/{status}")
    public ResponseEntity<?> getByStatus(@PathVariable(name = "status") String status) {
        return new ResponseEntity<>(tripService.findByStatus(status), HttpStatus.OK);
    }
    @GetMapping("/date/{date}")
    public ResponseEntity<?> getByArrival(@PathVariable(name = "date") Timestamp date) throws ParseException {
        return new ResponseEntity<>(tripService.findByTimeArrival(date), HttpStatus.OK);
    }
    @GetMapping("/sort")
    public ResponseEntity<?> sortByTimeArrival() {
        return new ResponseEntity<>(tripService.sortTripByTimeArrival(), HttpStatus.OK);
    }


    //Trip of Company
    @GetMapping("/Company/{id}")
    public ResponseEntity<?> getAllTripOfCompany(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(tripService.getAllTripOfCompany(id), HttpStatus.OK);
    }
    @GetMapping("/Company/{CompanyId}/{id}")
    public ResponseEntity<?> getByIdOfCompany(@PathVariable(name = "id") Long id, @PathVariable(name = "CompanyId") Long CompanyId) {
        return new ResponseEntity<>(tripService.findByIdOfCompany(CompanyId, id), HttpStatus.OK);
    }
    @GetMapping("/Company/status/{CompanyId}&{status}")
    public ResponseEntity<?> getByStatusOfCompany(@PathVariable(name = "status") String status, @PathVariable(name = "CompanyId") Long CompanyId) {
        return new ResponseEntity<>(tripService.findByStatusOfCompany(CompanyId,status), HttpStatus.OK);
    }
    @GetMapping("/Company/date/{CompanyId}&{date}")
    public ResponseEntity<?> getByArrivalOfCompany(@PathVariable(name = "date") Timestamp date, @PathVariable(name = "CompanyId") Long CompanyId) throws ParseException {
        return new ResponseEntity<>(tripService.findByTimeArrivalOfCompany(CompanyId, date), HttpStatus.OK);
    }
    @GetMapping("/Company/sort/{CompanyId}")
    public ResponseEntity<?> sortByTimeArrivalOfCompany(@PathVariable(name = "CompanyId") Long CompanyId) {
        return new ResponseEntity<>(tripService.sortTripByTimeArrivalOfCompany(CompanyId), HttpStatus.OK);
    }
    @GetMapping("/Company/Vehicle/{id}/{coId}/{veId}")
    public ResponseEntity<?> getbyIdcompanyandVehicle(@PathVariable(name = "coId") Long companyId,@PathVariable(name = "id")Long id, @PathVariable(name = "veId") Long veId) {
        return new ResponseEntity<>(tripService.findAllByIdAndCompany_IdAndVehicle_Id(id,companyId,veId), HttpStatus.OK);
    }

    @GetMapping("/Company/Vehicle/status/{coId}/{veStatus}")
    public ResponseEntity<?> getbycompanyIdandVehicleStatus(@PathVariable(name = "coId")Long companyId, @PathVariable(name = "veStatus") String veId) {
        return new ResponseEntity<>(tripService.findAllByCompany_IdAndVehicle_Status(companyId,veId), HttpStatus.OK);
    }
    @GetMapping("/Company/{arrival}&{departure}")
    public ResponseEntity<?> getAllTripOfCompany(@PathVariable(name = "arrival") String arrival, @PathVariable(name = "departure") String departure) {
        return new ResponseEntity<>(tripService.findbyArrivalAndDepature(arrival,departure), HttpStatus.OK);
    }


    //Customer
    @GetMapping("/Customer/{arrival}&{departure}")
    public ResponseEntity<?> getAllTripOfCustomer(@PathVariable(name = "arrival") String arrival, @PathVariable(name = "departure") String departure) {
        return new ResponseEntity<>(tripService.findbyArrivalAndDepature(arrival,departure), HttpStatus.OK);
    }


    //===========================================================================================================
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