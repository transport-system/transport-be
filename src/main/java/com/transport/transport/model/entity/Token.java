package com.transport.transport.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.transport.transport.config.security.user.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Token {
    @Id
    @GeneratedValue
    public Long id;

    @Column(unique = true)
    public String token;

    public String tokenType;

    public boolean revoked;

    public boolean expired;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id")
    public Account user;
}
