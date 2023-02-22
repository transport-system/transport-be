package com.transport.transport.model.response.trip;

import com.transport.transport.model.response.trip.customer.TripForCustomer;
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
    private List<TripForCustomer> list_trip_Customer;
    private List<TripResponeOfConpany> list_trip_Company;
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

    public TripMsg(String message, List<TripForCustomer> list_trip, String arrival) {
        this.message = message;
        this.list_trip_Customer = list_trip;
    }

    public TripMsg(String message, List<TripResponeOfConpany> list_trip, Long company) {
        this.message = message;
        this.list_trip_Company = list_trip;
    }
}
