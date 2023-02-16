package com.transport.transport.model.request.route;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.transport.transport.model.entity.City;
import com.transport.transport.model.entity.Trip;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RouteRequest {
    @NotBlank
    private String city1;
    @NotBlank
    private String city2;

}
