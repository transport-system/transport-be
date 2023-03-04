package com.transport.transport.controller.route;

import com.transport.transport.common.EndpointConstant;
import com.transport.transport.service.RouteService;
import com.transport.transport.service.TripService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(EndpointConstant.Route.ROUTE_ENDPOINT)
@Api( tags = "Routes")
public class RouteController {

    private final RouteService routeService;

    @GetMapping("/")
    public ResponseEntity<?> getById() {
        return new ResponseEntity<>(routeService.propose(), HttpStatus.OK);
    }

}
