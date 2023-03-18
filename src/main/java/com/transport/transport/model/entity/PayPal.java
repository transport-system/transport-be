package com.transport.transport.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PayPal")
public class PayPal {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="paymentId")
    private String paymentId;

    @Column(name = "saleId")
    private String saleId;
    @Column(name="payerId")
    private String payerId;
    @Column(name="customerId")
    private Long customerId;
    @Column(name = "bookingID")
    private  Long bookingId;
    @Column(name = "tripId")
    private Long tripId;
}
