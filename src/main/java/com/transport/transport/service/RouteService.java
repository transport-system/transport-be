package com.transport.transport.service;

import com.transport.transport.model.entity.Route;
import com.transport.transport.model.request.route.RouteRequest;

import java.util.List;

public interface RouteService {
    Route create(String Departure, String Arrival);
    List<Route> allRoute();

    List<Route> propose();
}
