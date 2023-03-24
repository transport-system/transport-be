package com.transport.transport.model.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.transport.transport.config.security.user.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "voucher" , uniqueConstraints = @UniqueConstraint(columnNames = {"voucher_code"}))
public class Voucher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "voucher_id")
    private Long id;

    @Column(name = "voucher_code", unique = true)
    private String voucherCode;

    @Column(name = "created_time")
    private Timestamp createdTime;

    @Column(name = "start_time")
    private Timestamp startTime;

    @Column(name = "expired_time")
    private Timestamp expiredTime;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "status")
    private String status;

    @Column(name = "discount_value")
    private BigDecimal discountValue;

    @Column(name = "owner")
    private String owner;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id", referencedColumnName = "company_id")
    private Company company;

    @JsonBackReference
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "accountVoucher",
            joinColumns = @JoinColumn(name = "voucher_id", referencedColumnName = "voucher_id"),
            inverseJoinColumns = @JoinColumn(name = "account_id", referencedColumnName = "account_id"))
    private List<Account> accounts;

    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    private Customer customer;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "voucher" )
    private List<Booking> bookings;
}
