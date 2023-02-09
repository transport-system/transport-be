package com.transport.transport.model.response.jwt;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
    private String token;
    private String type = "Beared";
    private String name;
    private Collection<? extends GrantedAuthority> roles;

    public JwtResponse(String accessToken, String name, Collection<? extends GrantedAuthority> roles) {
        this.token = accessToken;
        this.name = name;
        this.roles = roles;
    }
}
