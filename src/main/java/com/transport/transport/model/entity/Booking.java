package com.transport.transport.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private Long id;

    @Column(name = "appointment_date")
    private Timestamp appointmentDate;

    @Column(name = "expire_appointment_date")
    private Timestamp expireAppointmentDate;

    @Column(name = "appointment_status")
    private Boolean appointmentStatus;

    @Column(name = "create_booking_time")
    private Timestamp createBookingTime;

    @Column(name = "exam_time")
    private Timestamp examTime;

    @Column(name = "note")
    private String note;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "status")
    private String status;

    @Column(name = "rejected_note")
    private String rejectedNote;


    @JsonManagedReference
    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<FreeSeat> freeSeats;


    @JsonManagedReference
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "booking_detail",
            joinColumns = @JoinColumn(name = "booking_id", referencedColumnName = "booking_id"),
            inverseJoinColumns = @JoinColumn(name = "trip_id", referencedColumnName = "trip_id")
    )
    private List<Trip> trips;


    @JsonManagedReference
    @OneToMany(mappedBy = "booking",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    private List<FeedBack> feedBacks;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "account_id")
    private Account account;
}
