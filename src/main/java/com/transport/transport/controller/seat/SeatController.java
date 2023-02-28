package com.transport.transport.controller.seat;

import com.transport.transport.common.EndpointConstant;
import com.transport.transport.mapper.SeatMapper;
import com.transport.transport.model.entity.FreeSeat;
import com.transport.transport.model.request.booking.BookingRequest;
import com.transport.transport.model.response.seat.SeatMsg;
import com.transport.transport.model.response.seat.SeatResponse;
import com.transport.transport.service.SeatService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = EndpointConstant.Seat.SEAT_ENDPOINT)
@Api( tags = "Seats")
public class SeatController {
    private final SeatService seatService;
    private final SeatMapper seatMapper;

    @GetMapping("vehicle/{id}")
    public ResponseEntity<?> findAll(@PathVariable("id") Long vehicleId) {
        List<FreeSeat> freeSeats = seatService.findAllSeatByVehicle(vehicleId);
        List<SeatResponse> seatResponses = seatMapper.mapSeatResponseFromFreeSeat(freeSeats);
        return new ResponseEntity<>(new SeatMsg("Get all seat success", seatResponses),
                null, 200);
    }
}
