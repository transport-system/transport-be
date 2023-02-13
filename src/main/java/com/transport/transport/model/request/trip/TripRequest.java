package com.transport.transport.model.request.trip;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;

public class TripRequest {
    @NotBlank
    private String employeeName;
    @NotNull
    @Min(10000)
    @Max(100000000)
    private double price;
    @NotBlank
    private String image;
    @NotBlank
    private String description;
    @NotNull
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date timeArrival;
    @NotNull
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date timeReturn;
    @NotNull
    private Long vehicleId;
    @NotNull
    private Long routeId;
    @NotNull
    private Long companyId;
}
