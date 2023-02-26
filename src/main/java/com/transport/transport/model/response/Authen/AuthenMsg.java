package com.transport.transport.model.response.Authen;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthenMsg {
    private String message;
    private List<AuthenticationResponse> list;
    private AuthenticationResponse data;


    public AuthenMsg(String message) {
        this.message = message;
    }

    public AuthenMsg(String message, AuthenticationResponse accountResponse) {
        this.message = message;
        this.data = accountResponse;
    }

    public AuthenMsg(String message, List<AuthenticationResponse> list) {
        this.message = message;
        this.list = list;
    }
}
