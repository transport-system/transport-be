package com.transport.transport.model.request.vehicle;

import com.transport.transport.common.Status;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VehicleRequest {
    @NotBlank
    private String licensePlates;
    @NotBlank
    private String vehicle_type_name;
    private Long companyId;
    private Status.Vehicle vehicle;
}
