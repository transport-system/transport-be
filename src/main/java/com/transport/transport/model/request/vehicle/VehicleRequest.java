package com.transport.transport.model.request.vehicle;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleRequest implements Serializable {
    @NotBlank
    private String licensePlates;
    @NotBlank
    private String vehicleType;
    private Long companyId;
}
