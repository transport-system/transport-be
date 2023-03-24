package com.transport.transport.model.response.trip.customer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.transport.transport.model.entity.Company;
import com.transport.transport.model.entity.Vehicle;
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
public class TripForCustomer {

    private Long tripId;
    private Long companyId;
    private String employeeName;
    private double price;
    private String image;
    private String description;
    private Timestamp timeArrival;
    private Timestamp timeDeparture;
    private Timestamp timeReturn;
    private int seatQuantity;
    private Company company;
    private String status;
    private String arrival;
    private String departure;

    private VehicleResponse vehicle;
}
