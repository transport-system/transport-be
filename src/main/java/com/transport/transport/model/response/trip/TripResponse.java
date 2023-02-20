package com.transport.transport.model.response.trip;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.transport.transport.model.entity.City;
import com.transport.transport.model.entity.Route;
import com.transport.transport.model.response.route.RouteResponse;
import com.transport.transport.model.response.vehicle.VehicleResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TripResponse {
    private Long Id;
    private String employeeName;
    private double price;
    private String image;
    private String description;
    private Timestamp timeArrival;
    private Timestamp timeDeparture;
    private Timestamp timeReturn;
    private int seatQuantity;
    private String status;
    private Route route;
    private City arrival;
    private City departure;
}
