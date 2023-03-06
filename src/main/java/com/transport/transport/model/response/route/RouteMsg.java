package com.transport.transport.model.response.route;

import com.transport.transport.model.response.feeedback.FeedbackResponse;

import java.util.List;

public class RouteMsg {
    private String message;
    private RoutePropose data;
    private List<RoutePropose> list_trip;
    public RouteMsg(String message, RoutePropose Response) {
        this.message = message;
        this.data = Response;
    }
    public RouteMsg(String message, List<RoutePropose> list) {
        this.message = message;
        this.list_trip = list;
    }
}
