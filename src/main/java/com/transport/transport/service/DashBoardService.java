package com.transport.transport.service;

import java.math.BigDecimal;

public interface DashBoardService {
    int countUser();
    int countCompany();
    int countTrip();
    int countBookingByCompany(Long id);

    int countBookingByTrip(Long id);
    BigDecimal revenue();
    BigDecimal revenueByCompany();

}
