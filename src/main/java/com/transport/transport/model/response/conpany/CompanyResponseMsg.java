package com.transport.transport.model.response.conpany;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompanyResponseMsg {
    private String message;
    private CompanyResponse data;

    public CompanyResponseMsg(String message, CompanyResponse data) {
        this.message = message;
        this.data = data;
    }

    public CompanyResponseMsg() {
    }

    public CompanyResponseMsg(String message) {
        this.message = message;
    }
}

