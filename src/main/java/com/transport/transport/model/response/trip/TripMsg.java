package com.transport.transport.model.response.trip;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TripMsg {
    private String message;
    private TripResponse data;
    public TripMsg(String message) {
        this.message = message;
    }
    public TripMsg(String message, TripResponse tripResponse) {
        this.message = message;
        this.data = tripResponse;
    }
}
