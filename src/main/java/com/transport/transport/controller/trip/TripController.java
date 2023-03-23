package com.transport.transport.controller.trip;

import com.transport.transport.common.EndpointConstant;
import com.transport.transport.mapper.RouteMapper;
import com.transport.transport.mapper.TripMapper;
import com.transport.transport.model.entity.Route;
import com.transport.transport.model.entity.Trip;
import com.transport.transport.model.request.trip.TripRequest;
import com.transport.transport.model.request.trip.UpdateTrip;
import com.transport.transport.model.response.route.RouteMsg;
import com.transport.transport.model.response.route.RoutePropose;
import com.transport.transport.model.response.trip.TripMsg;
import com.transport.transport.model.response.trip.TripResponeOfConpany;
import com.transport.transport.model.response.trip.TripResponse;
import com.transport.transport.model.response.trip.TripResponseForId;
import com.transport.transport.model.response.trip.customer.TripForCustomer;
import com.transport.transport.service.RouteService;
import com.transport.transport.service.TripService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(EndpointConstant.Trip.TRIP_ENDPOINT)
@Api( tags = "Trips")
public class TripController {
    private final TripService tripService;
    private final TripMapper tripMapper;

    //Trip Of Admin
    @ApiOperation(value = "This is method get all trip.")
    @GetMapping("/all")
    public ResponseEntity<TripMsg> getAll() {
        List<Trip> trip = tripService.getAllTrip();
        List<TripResponse> list =  tripMapper.mapToTripResponse(trip);
        return new ResponseEntity<>(new TripMsg("List", list), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable(name = "id") Long id) {
        Trip trip1 = tripService.findById(id);
        TripResponseForId trip = tripMapper.mapTripResponseIdFromTrip(trip1);
        return new ResponseEntity<>(new TripMsg("List", trip), HttpStatus.OK);
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
    @GetMapping("/company/{id}")
    public ResponseEntity<?> getAllTripOfCompany(@PathVariable(name = "id") Long id) {
        List<Trip> trip = tripService.getAllTripOfCompany(id);
        List<TripResponeOfConpany> listTrip =  tripMapper.mapToTripResponseOfCompany(trip);
        return new ResponseEntity<>(new TripMsg("List", listTrip, id), HttpStatus.OK);
    }
    @GetMapping("/company/{CompanyId}/{id}")
    public ResponseEntity<?> getByIdOfCompany(@PathVariable(name = "id") Long id,
                                              @PathVariable(name = "CompanyId") Long CompanyId) {
        return new ResponseEntity<>(tripService.findByIdOfCompany(CompanyId, id), HttpStatus.OK);
    }
    @GetMapping("/company/status/{CompanyId}/{status}")
    public ResponseEntity<?> getByStatusOfCompany(@PathVariable(name = "status") String status, @PathVariable(name = "CompanyId") Long CompanyId) {
        return new ResponseEntity<>(tripService.findByStatusOfCompany(CompanyId,status), HttpStatus.OK);
    }
    @GetMapping("/company/date/{CompanyId}/{date}")
    public ResponseEntity<?> getByArrivalOfCompany(@PathVariable(name = "date") Timestamp date,
                                                   @PathVariable(name = "CompanyId") Long CompanyId) throws ParseException {
        return new ResponseEntity<>(tripService.findByTimeArrivalOfCompany(CompanyId, date), HttpStatus.OK);
    }

    @GetMapping("/company/sort/{CompanyId}")
    public ResponseEntity<?> sortByTimeArrivalOfCompany(@PathVariable(name = "CompanyId") Long CompanyId) {
        return new ResponseEntity<>(tripService.sortTripByTimeArrivalOfCompany(CompanyId), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCompany(@PathVariable(name = "id") Long id) {
        tripService.deleteTrip(id);
        return new ResponseEntity<>("Success",HttpStatus.OK);
    }


    //Customer
    @GetMapping("/customer/{arrival}/{departure}/{date}")
    public ResponseEntity<?> getAllTripOfCustomerWithTime(@PathVariable(name = "arrival") String arrival,
                                                  @PathVariable(name = "departure") String departure,
                                                  @PathVariable(name = "date") String date){
        List<Trip> trip = tripService.findbyArrival_DepatureAndTime(arrival,departure,date);
        List<TripForCustomer> listTrip =  tripMapper.mapToTripResponseOfCustomer(trip);
        return new ResponseEntity<>(new TripMsg("List", listTrip, arrival), HttpStatus.OK);
    }
    @GetMapping("/customer/{arrival}/{departure}")
    public ResponseEntity<?> getAllTripOfCustomer(@PathVariable(name = "arrival") String arrival,
                                                  @PathVariable(name = "departure") String departure){
        List<Trip> trip = tripService.findbyArrivalAndDepature(arrival,departure);
        List<TripForCustomer> listTrip =  tripMapper.mapToTripResponseOfCustomer(trip);
        return new ResponseEntity<>(new TripMsg("List", listTrip, arrival), HttpStatus.OK);
    }

    //===========================================================================================================
    @PreAuthorize("hasAuthority(T(com.transport.transport.common.RoleEnum).COMPANY)")
    @PostMapping("/create")
    public ResponseEntity<TripMsg> create(@Valid @RequestBody TripRequest request) {
        Trip trip = tripService.createrTrip(request);
        TripResponse response = tripMapper.mapTripResponseFromTrip(trip);
        return new ResponseEntity<>(new TripMsg("create success", response), null, 200);
    }
    @ApiOperation(value = "This is method for update Trip.")
    @PreAuthorize("hasAuthority(T(com.transport.transport.common.RoleEnum).COMPANY)")
    @PutMapping("update")
    public ResponseEntity<TripMsg> update(@Valid @RequestBody UpdateTrip request) {
        Trip trip = tripService.updateTrip(request);
        TripResponse response = tripMapper.mapTripResponseFromTrip(trip);
        return new ResponseEntity<>(new TripMsg("update success",
                response), null, 200);
    }
    @ApiOperation(value = "This is method for disable/enable update status.")
    @PreAuthorize("hasAuthority(T(com.transport.transport.common.RoleEnum).COMPANY)")
    @GetMapping("/switchStatus/{id}")
    public ResponseEntity<?> switchStatus(@PathVariable(name = "id") Long id) {
        tripService.switchStatusTrip(id);
        return new ResponseEntity<>("successfully disable/enable status", HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority(T(com.transport.transport.common.RoleEnum).ADMIN)")
    @PostMapping("/city/{city}")
    public ResponseEntity<?> addCity(@PathVariable(name = "city") String city) {
        return new ResponseEntity<>(tripService.addCity(city), HttpStatus.OK);
    }
}