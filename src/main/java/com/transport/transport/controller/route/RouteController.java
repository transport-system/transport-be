package com.transport.transport.controller.route;

import com.transport.transport.common.EndpointConstant;
import com.transport.transport.mapper.RouteMapper;
import com.transport.transport.model.entity.Route;
import com.transport.transport.model.entity.Trip;
import com.transport.transport.model.response.route.RouteMsg;
import com.transport.transport.model.response.route.RoutePropose;
import com.transport.transport.model.response.trip.TripMsg;
import com.transport.transport.model.response.trip.TripResponse;
import com.transport.transport.service.RouteService;
import com.transport.transport.service.TripService;
import io.swagger.annotations.Api;
import io.swagger.annotations.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Clock;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(EndpointConstant.Route.ROUTE_ENDPOINT)
@Api( tags="Routes")
public class RouteController {

    private final RouteService routeService;
    private final RouteMapper routeMapper;

    @GetMapping("/propose")
    public ResponseEntity<List<RoutePropose>> propose() {
        List<Route> route = routeService.propose();
        List<RoutePropose> list =  routeMapper.mapRouteFromRoutePropose(route);
        return new ResponseEntity<List<RoutePropose>>(list, HttpStatus.OK);
    }

}
