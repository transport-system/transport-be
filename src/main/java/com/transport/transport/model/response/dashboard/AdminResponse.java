package com.transport.transport.model.response.dashboard;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdminResponse {
    private int totalCompany;
    private int totalUser;
    private int totalTrip;
    private BigDecimal revenue;
}
