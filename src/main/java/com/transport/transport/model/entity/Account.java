package com.transport.transport.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter @Setter
@Builder
@AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "accounts",
        uniqueConstraints =
        @UniqueConstraint(columnNames = {"email", "phone", "username"}))
public class Account {
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
    private String status;

    @Column(name = "role")
    private String role;

    @JsonManagedReference
    @OneToMany(mappedBy = "account",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<FeedBack> feedBacks;

    @JsonManagedReference
    @OneToMany(mappedBy = "account",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Booking> bookings;

    @JsonBackReference
    @OneToOne(mappedBy = "account",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Company company;
}
