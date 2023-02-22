package com.transport.transport.model.request.trip;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
}
