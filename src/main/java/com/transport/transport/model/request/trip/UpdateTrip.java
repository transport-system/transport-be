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
public class UpdateTrip implements Serializable {

    private Long tripId;

    private String employeeName;

    private double price;

    private String description;

    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm")
    private Timestamp timeDeparture;

    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm")
    private Timestamp timeArrival;
}
