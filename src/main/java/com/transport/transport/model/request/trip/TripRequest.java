package com.transport.transport.model.request.trip;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripRequest implements Serializable {
    @NotBlank
    private String employeeName;
    @NotNull
    @Min(10000)
    @Max(100000000)
    private double price;
    private String image;
    @NotBlank
    private String description;
    @NotNull
    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Timestamp timeDeparture;
    @NotNull
    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Timestamp timeArrival;
    @NotNull
    private Long vehicleId;
    @NotNull
    private String cityDeparture;
    @NotNull
    private String cityArrival;
    @NotNull
    private Long companyId;
    private  String specialDay = "False";
}
