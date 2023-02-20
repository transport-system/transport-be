package com.transport.transport.model.response.conpany;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.transport.transport.model.response.vehicle.VehicleResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompanyResponseMsg {
    private String message;
    private Long companyId;
    private CompanyResponse data;
    private List<CompanyResponse> list_data;
    private List<VehicleResponse> list_vehicle;
    public CompanyResponseMsg(String message, CompanyResponse data) {
        this.message = message;
        this.data = data;
    }

    public CompanyResponseMsg(String message, List<CompanyResponse> list_data) {
        this.message = message;
        this.list_data = list_data;
    }

    public CompanyResponseMsg(String message, Long companyId, List<VehicleResponse> list_vehicle) {
        this.message = message;
        this.companyId = companyId;
        this.list_vehicle = list_vehicle;
    }

    public CompanyResponseMsg() {
    }

    public CompanyResponseMsg(String message) {
        this.message = message;
    }
}

