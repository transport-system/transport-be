package com.transport.transport.service;

import com.transport.transport.model.entity.Route;
import com.transport.transport.model.request.route.RouteRequest;

public interface RouteService {
    Route create(String Departure, String Arrival);
}
