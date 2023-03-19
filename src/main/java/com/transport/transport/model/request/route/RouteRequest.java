package com.transport.transport.model.request.route;

import lombok.*;

import javax.validation.constraints.NotBlank;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouteRequest {
    @NotBlank
    private String city1;
    @NotBlank
    private String city2;

}
