package com.transport.transport.service.impl.dashboard;

import com.transport.transport.common.RoleEnum;
import com.transport.transport.common.Status;
import com.transport.transport.exception.NotFoundException;
import com.transport.transport.model.entity.Booking;
import com.transport.transport.model.response.dashboard.AdminResponse;
import com.transport.transport.model.response.dashboard.CompanyResponse;
import com.transport.transport.model.response.dashboard.RevenueByMonth;
import com.transport.transport.repository.AccountRepository;
import com.transport.transport.repository.BookingRepository;
import com.transport.transport.repository.CompanyRepository;
import com.transport.transport.repository.TripRepository;
import com.transport.transport.service.DashBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DashBoardServiceImpl implements DashBoardService {
    private final CompanyRepository companyRepository;
    private final TripRepository tripRepository;
    private final AccountRepository accountRepository;
    private final BookingRepository bookingRepository;

    @Override
    public AdminResponse getAdminDashboard() {
        AdminResponse adminResponse = new AdminResponse();
        adminResponse.setTotalCompany(accountRepository.countAccountsByRole(RoleEnum.COMPANY.name()));
        adminResponse.setTotalUser(accountRepository.countAccountsByRole(RoleEnum.USER.name()));
        adminResponse.setTotalBooking(bookingRepository.countTotalBooking());
        adminResponse.setTotalBookingCancel(bookingRepository.countTotalBookingCancel());
        adminResponse.setTotalBookingPending(bookingRepository.countTotalBookingPending());
        adminResponse.setTotalBookingSuccess(bookingRepository.countTotalBookingSuccess());
        adminResponse.setRevenue(bookingRepository.getRevenue());
        return adminResponse;
    }

    @Override
    public CompanyResponse getCompanyDashboard(Long companyId) {
        if (companyRepository.findById(companyId).isEmpty()) {
            throw new NotFoundException("Company not found");
        } else {
            CompanyResponse companyResponse = new CompanyResponse();
            companyResponse.setTotalCustomer(accountRepository.countAccountsByRoleAndCompanyId(RoleEnum.USER.name(), companyId));
            companyResponse.setTotalTrip(tripRepository.countTotalTripByCompanyId(companyId));
            companyResponse.setTotalVehicle(tripRepository.countTotalVehicleByCompanyId(companyId));
            companyResponse.setTotalBooking(bookingRepository.countTotalBookingByCompanyId(companyId));
            companyResponse.setTotalBookingCancel(bookingRepository.countTotalBookingByCompanyIdAndStatus(companyId,
                    Status.Booking.REJECTED.name()));
            companyResponse.setTotalBookingSuccess(bookingRepository.countTotalBookingByCompanyIdAndStatus(companyId,
                    Status.Booking.DONE.name()));
            companyResponse.setTotalBookingPending(bookingRepository.countTotalBookingByCompanyIdAndStatus(companyId,
                    Status.Booking.PENDING.name()));
            companyResponse.setTotalRevenue(bookingRepository.getRevenueByCompanyId(companyId));
            return companyResponse;
        }
    }

    @Override
    public CompanyResponse getCompanyDashboard(Long companyId, Timestamp from, Timestamp to) {
        CompanyResponse companyResponse = new CompanyResponse();
        companyResponse.setTotalTrip(tripRepository.countTotalTripByCompanyId(companyId, from, to));
        companyResponse.setTotalRevenue(bookingRepository.getRevenueByCompanyId(companyId, from, to));
        companyResponse.setTotalBooking(bookingRepository.countTotalBookingByCompanyId(companyId, from, to));
        companyResponse.setTotalBookingCancel(bookingRepository.countTotalBookingByCompanyIdAndStatus(companyId,
                Status.Booking.REJECTED.name(), from, to));
        companyResponse.setTotalBookingSuccess(bookingRepository.countTotalBookingByCompanyIdAndStatus(companyId,
                Status.Booking.DONE.name(), from, to));
        companyResponse.setTotalBookingPending(bookingRepository.countTotalBookingByCompanyIdAndStatus(companyId,
                Status.Booking.PENDING.name(), from, to));
        return companyResponse;
    }


    @Override
    public List<RevenueByMonth> getRevenueByMonth(Long companyId) {
        RevenueByMonth revenueByMonth = new RevenueByMonth();
        List<Object[]> list = new ArrayList<>();
        List<RevenueByMonth> revenueByMonths = new ArrayList<>();
        Map<Integer, BigDecimal> map = new HashMap<>();
        list = bookingRepository.getRevenueByMonth(companyId);
        for (Object[] obj : list) {
            map.put((Integer) obj[0], (BigDecimal) obj[1]);
        }

        for (int i = 1; i <= 12; i++) {
            revenueByMonth = new RevenueByMonth();
            revenueByMonth.setMonth(i);
            if (map.containsKey((i))) {
                revenueByMonth.setRevenue(map.get(i));
            } else {
                revenueByMonth.setRevenue(BigDecimal.ZERO);
            }
            revenueByMonths.add(revenueByMonth);
        }
        return revenueByMonths;
    }


}
