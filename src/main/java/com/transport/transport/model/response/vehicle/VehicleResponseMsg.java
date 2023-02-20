package com.transport.transport.model.response.vehicle;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VehicleResponseMsg {
    private String message;
    private VehicleResponse data;
    private String status;
    private List<VehicleResponse> list;

    public VehicleResponseMsg(String message, VehicleResponse data) {
        this.message = message;
        this.data = data;
    }

    public VehicleResponseMsg(String message) {
        this.message = message;
    }

    public VehicleResponseMsg(String message, String status, List<VehicleResponse> list) {
        this.message = message;
        this.status = status;
        this.list = list;
    }

    public VehicleResponseMsg(String message, List<VehicleResponse> list) {
        this.message = message;
        this.list = list;
    }
}
