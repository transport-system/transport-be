package com.transport.transport.service.impl.dashboard;

import com.transport.transport.common.RoleEnum;
import com.transport.transport.repository.AccountRepository;
import com.transport.transport.repository.BookingRepository;
import com.transport.transport.repository.CompanyRepository;
import com.transport.transport.repository.TripRepository;
import com.transport.transport.service.DashBoardService;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class DashBoardServiceImpl implements DashBoardService {
    private final CompanyRepository companyRepository;
    private final TripRepository tripRepository;
    private final AccountRepository accountRepository;
    private final BookingRepository bookingRepository;

    @Override
    public int countUser() {
        return accountRepository.countAccountsByRole(RoleEnum.USER.name());
    }

    @Override
    public int countCompany() {
        return companyRepository.countAllCompany();
    }

    @Override
    public int countTrip() {
        return tripRepository.countAllTrip();
    }

    @Override
    public int countBookingByCompany(Long id) {
        return bookingRepository.countAllByTrip_Company_Account_Id(id);
    }

    @Override
    public int countBookingByTrip(Long id) {
        return bookingRepository.countAllByTrip_Id(id);
    }

    @Override
    public BigDecimal revenue() {
        return bookingRepository.revenue();
    }

    @Override
    public BigDecimal revenueByCompany() {
        return bookingRepository.revenueByCompany();
    }

}
