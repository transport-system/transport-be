package com.transport.transport.model.response.trip;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TripMsg {
    private String message;
    private TripResponse data;
    private List<TripResponse> list_trip;
    public TripMsg(String message) {
        this.message = message;
    }
    public TripMsg(String message, TripResponse tripResponse) {
        this.message = message;
        this.data = tripResponse;
    }
    public TripMsg(String message, List<TripResponse> list_trip) {
        this.message = message;
        this.list_trip = list_trip;
    }
}
