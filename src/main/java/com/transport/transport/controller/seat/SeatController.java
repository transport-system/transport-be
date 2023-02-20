package com.transport.transport.controller.seat;

import com.transport.transport.common.EndpointConstant;
import com.transport.transport.mapper.SeatMapper;
import com.transport.transport.model.entity.FreeSeat;
import com.transport.transport.model.request.booking.BookingRequest;
import com.transport.transport.model.response.seat.SeatResponse;
import com.transport.transport.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = EndpointConstant.Seat.SEAT_ENDPOINT)
public class SeatController {
    private final SeatService seatService;
    private final SeatMapper seatMapper;
}
