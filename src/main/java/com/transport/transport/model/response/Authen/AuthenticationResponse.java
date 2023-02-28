package com.transport.transport.model.response.Authen;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthenticationResponse {
    private Long id;
    private String username;
    private String firstname;
    private String lastname;
    private String token;
    private String role;
    private Long companyId;
}
