package com.transport.transport.repository;

import com.transport.transport.model.entity.PayPal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayPalRepository extends JpaRepository<PayPal, Long> {
    PayPal findByBookingId(Long bookingId);
}
