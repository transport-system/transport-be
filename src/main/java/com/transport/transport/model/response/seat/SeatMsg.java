package com.transport.transport.model.response.seat;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SeatMsg {
    private String message;
    private List<SeatResponse> data;

    public SeatMsg(String message) {
        this.message = message;
    }

    public SeatMsg(String message, List<SeatResponse> list) {
        this.message = message;
        this.data = list;
    }
}
