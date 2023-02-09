package com.transport.transport.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.transport.transport.common.RoleEnum;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.List;

@Getter @Setter
@Builder
@AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "accounts",
        uniqueConstraints =
        @UniqueConstraint(columnNames = {"email", "phone", "username"}))
public class Account implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "account_id")
    private Long id;
    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;
    @Column(name = "firstname")
    private String firstname;
    @Column(name = "lastname")
    private String lastname;
    private String email;

    @Column(name = "avatar_image")
    private String avatarImage;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "phone")
    private String phone;

    @Column(name = "gender")
    private String gender;

    @Column(name = "status")
    private String status = "PENDING";

    @Enumerated(EnumType.STRING)
    @Column(name = "Role")
    private RoleEnum role;

    @JsonBackReference
    @OneToMany(mappedBy = "account",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<FeedBack> feedBacks;

    @JsonBackReference
    @OneToMany(mappedBy = "account",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Booking> bookings;

    @JsonBackReference
    @OneToOne(mappedBy = "account",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Company company;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
