package com.transport.transport.model.response.trip;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.transport.transport.model.response.vehicle.VehicleResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TripResponse implements Serializable {
    private Long tripId;
    private Long companyId;
    private Long vehicleId;
    private String vehicleType;
    private String employeeName;
    private double price;
    private String image;
    private String description;
    private Timestamp timeArrival;
    private Timestamp timeDeparture;
    private Timestamp timeReturn;
    private int seatQuantity;
    private String status;
    private String arrival;
    private String departure;
    private VehicleResponse vehicle;
    private  String specialDay;
}
