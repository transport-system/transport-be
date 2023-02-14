package com.transport.transport.model.response.trip;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

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
    private Date timeArrival;
    private Date timeReturn;
    private int maxSeat;
    private String status;

}
