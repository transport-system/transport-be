package com.transport.transport.common;

import org.springframework.security.core.GrantedAuthority;

public enum RoleEnum implements GrantedAuthority {
    USER,
    ADMIN,
    COMPANY;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
