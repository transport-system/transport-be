package com.transport.transport.model.request.vehicle;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleRequest {
    @NotBlank
    private String licensePlates;
    @NotBlank
    private String vehicleType;
    private Long companyId;
}
