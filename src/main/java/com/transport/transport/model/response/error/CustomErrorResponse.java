package com.transport.transport.model.response.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomErrorResponse {
    private Timestamp timeStamp;
    private int status;
    private String error;
    private List<String> message;
}
