package com.transport.transport.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "trip")
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trip_id")
    private Long id;

    @Column(name = "employee_name")
    private String employeeName;

    @Column(name = "price")
    private double price;

    @Column(name = "trip_image")
    private String image;

    @Column(name = "description")
    private String description;

    @Column(name = "time_departure")
    private Timestamp timeDeparture;

    @Column(name = "time_arrival")
    private Timestamp timeArrival;

    @Column(name = "time_return")
    private Timestamp timeReturn;

    @Column(name = "seat_quantity")
    private int seatQuantity;

    @Column(name = "status")
    private String status;

    @Column(name = "allow_payLater ")
    private boolean allowPayLater ;
    @JsonBackReference
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "vehicle_id", referencedColumnName = "vehicle_id")
    private Vehicle vehicle;


    @JsonManagedReference
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "route_id", referencedColumnName = "route_id")
    private Route route;

    @JsonManagedReference
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id", referencedColumnName = "company_id")
    private Company company;


    @JsonBackReference
    @OneToMany(mappedBy = "trip",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<Booking> bookings;
}

