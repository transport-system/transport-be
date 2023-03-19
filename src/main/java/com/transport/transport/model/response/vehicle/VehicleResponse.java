package com.transport.transport.model.response.vehicle;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VehicleResponse {
    private Long vehicleId;
    private Long companyId;
    private int totalSeat;
    private String licensePlates;
    private String status;
    private String vehicleType;
}
