package com.transport.transport.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.transport.transport.common.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
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

    @Column(name = "max_seat")
    private int maxSeat;


    @Column(name = "status")
    private String status;


    @JsonManagedReference
    @ManyToOne(optional = false)
    @JoinColumn(name = "vehicle_id", referencedColumnName = "vehicle_id")
    private Vehicle vehicle;


    @JsonManagedReference
    @ManyToOne(optional = false)
    @JoinColumn(name = "route_id", referencedColumnName = "route_id")
    private Route route;

    @JsonBackReference
    @ManyToOne(optional = false)
    @JoinColumn(name = "company_id", referencedColumnName = "company_id")
    private Company company;


    @JsonBackReference
    @ManyToMany(mappedBy = "trips",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<Booking> bookings;
}

