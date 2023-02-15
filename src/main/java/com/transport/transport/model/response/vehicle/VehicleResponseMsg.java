package com.transport.transport.model.response.vehicle;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.transport.transport.common.Status;
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
    private String name;
    private List<VehicleResponse> list;


    public VehicleResponseMsg(String message, VehicleResponse data) {
        this.message = message;
        this.data = data;
    }

    public VehicleResponseMsg(String message) {
        this.message = message;
    }

    public VehicleResponseMsg(String message, String name, List<VehicleResponse> list) {
        this.message = message;
        this.name = name;
        this.list = list;
    }

}
