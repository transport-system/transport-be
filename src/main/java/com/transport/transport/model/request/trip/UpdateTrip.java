package com.transport.transport.model.request.trip;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateTrip {
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
    @NotBlank
    private String status;

}
