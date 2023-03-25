package com.transport.transport.service.impl.dashboard;

import com.transport.transport.common.PaymentType;
import com.transport.transport.common.RoleEnum;
import com.transport.transport.common.Status;
import com.transport.transport.exception.NotFoundException;
import com.transport.transport.model.response.dashboard.AdminResponse;
import com.transport.transport.model.response.dashboard.CompanyResponse;
import com.transport.transport.model.response.dashboard.RevenueByMonth;
import com.transport.transport.repository.*;
import com.transport.transport.service.DashBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    private final VehicleRepository vehicleRepository;

    @Override
    public AdminResponse getAdminDashboard() {
        AdminResponse adminResponse = new AdminResponse();
        adminResponse.setTotalCompany(accountRepository.countAccountsByRole(RoleEnum.COMPANY.name()));
        adminResponse.setTotalUser(accountRepository.countAccountsByRole(RoleEnum.USER.name()));
        adminResponse.setTotalTrip(tripRepository.countTotalTrip());
        adminResponse.setRevenue(bookingRepository.getRevenue());
        return adminResponse;
    }

    @Override
    public AdminResponse getAdminDashboardLast7Days() {
        AdminResponse adminResponse = new AdminResponse();
        return adminResponse;
    }

    @Override
    public CompanyResponse getCompanyDashboard(Long companyId) {
        if (companyRepository.findById(companyId).isEmpty()) {
            throw new NotFoundException("Company not found");
        } else {
            CompanyResponse companyResponse = new CompanyResponse();
            companyResponse.setTotalCustomer(accountRepository.countTotalCustomerByCompanyId(companyId));
            companyResponse.setTotalTrip(tripRepository.countTotalTripByCompanyId(companyId));
//            companyResponse.setTotalVehicle(vehicleRepository.countTotalVehicleByCompanyId(companyId));
            companyResponse.setTotalBooking(bookingRepository.countTotalBookingByCompanyId(companyId));
            companyResponse.setTotalBookingPending(bookingRepository.countTotalBookingByCompanyIdAndStatus(companyId,
                    Status.Booking.PENDING.name()));
            companyResponse.setTotalBookingDone(bookingRepository.countTotalBookingByCompanyIdAndStatus(companyId,
                    Status.Booking.DONE.name()));
            companyResponse.setTotalBookingTimeout(bookingRepository.countTotalBookingByCompanyIdAndStatus(companyId,
                    Status.Booking.REJECTED.name()));
            companyResponse.setTotalBookingRefunded(bookingRepository.countTotalBookingByCompanyIdAndStatus(companyId,
                    Status.Booking.REFUNDED.name()));
            companyResponse.setTotalBookingRequestRefund(bookingRepository.countTotalBookingByCompanyIdAndStatus(companyId,
                    Status.Booking.REQUESTREFUND.name()));
            companyResponse.setTotalBookingAwaitPayment(bookingRepository.countTotalBookingByCompanyIdAndStatus(companyId,
                    Status.Booking.PAYLATER.name()));
            companyResponse.setTotalRevenue(bookingRepository.getRevenueByCompanyId(companyId));
            companyResponse.setTotalVoucherHave(bookingRepository.countTotalVoucherHaveByCompanyId(companyId));
            //companyResponse.setTotalBookingPaymentCard(bookingRepository.countTotalBookingByTotalPayMenthodwithCompanyID(companyId, PaymentType.CARD.name()));
           // companyResponse.setTotalBookingPaymentCash(bookingRepository.countTotalBookingByTotalPayMenthodwithCompanyID(companyId, PaymentType.CASH.name()));
          //  companyResponse.setTotalVoucherIsBooked(bookingRepository.countTotalVoucherisBookedByCompanyId(companyId));
            return companyResponse;
        }
    }

    @Override
    public CompanyResponse getCompanyDashboardLast7Days(Long companyId) {
        if (companyRepository.findById(companyId).isEmpty()) {
            throw new NotFoundException("Company not found");
        }
        else if(accountRepository.countTotalCustomerByCompanyIdLast7Days(companyId)==0){
            throw new NotFoundException("Date khong ton tai trong 7day ");
        }
        else {
            CompanyResponse companyResponse = new CompanyResponse();
            companyResponse.setTotalCustomer(accountRepository.countTotalCustomerByCompanyIdLast7Days(companyId));
            companyResponse.setTotalTrip(tripRepository.countTotalTripByCompanyIdLast7Days(companyId));
            companyResponse.setTotalBooking(bookingRepository.countTotalBookingByCompanyIdLast7Days(companyId));
            companyResponse.setTotalBookingPending(bookingRepository.countTotalBookingByCompanyIdAndStatusLast7Days(companyId,
                    Status.Booking.PENDING.name()));
            companyResponse.setTotalBookingDone(bookingRepository.countTotalBookingByCompanyIdAndStatusLast7Days(companyId,
                    Status.Booking.DONE.name()));
            companyResponse.setTotalBookingTimeout(bookingRepository.countTotalBookingByCompanyIdAndStatusLast7Days(companyId,
                    Status.Booking.REJECTED.name()));
            companyResponse.setTotalBookingRefunded(bookingRepository.countTotalBookingByCompanyIdAndStatusLast7Days(companyId,
                    Status.Booking.REFUNDED.name()));
            companyResponse.setTotalBookingRequestRefund(bookingRepository.countTotalBookingByCompanyIdAndStatusLast7Days(companyId,
                    Status.Booking.REQUESTREFUND.name()));
            companyResponse.setTotalBookingAwaitPayment(bookingRepository.countTotalBookingByCompanyIdAndStatusLast7Days(companyId,
                    Status.Booking.PAYLATER.name()));
            companyResponse.setTotalRevenue(bookingRepository.getRevenueByCompanyIdLast7Days(companyId));
            return companyResponse;
        }
    }

    @Override
    public CompanyResponse getCompanyByTripDashboard(Long companyId, Long tripId) {
        if (companyRepository.findById(companyId).isEmpty()) {
            throw new NotFoundException("Company not found");
        } else {
            CompanyResponse companyResponse = new CompanyResponse();
            companyResponse.setTotalCustomer(accountRepository
                    .countTotalCustomerByCompanyIdAndTripId(companyId, tripId));
            companyResponse.setTotalTrip(tripRepository
                    .countTotalTripByCompanyIdAndTripId(companyId,  tripId));
            companyResponse.setTotalBooking(bookingRepository
                    .countTotalBookingByCompanyIdAndTripId(companyId, tripId));
            companyResponse.setTotalBookingPending(bookingRepository
                    .countTotalBookingByCompanyIdAndTripIdAndStatus(companyId, tripId, Status.Booking.PENDING.name()));
            companyResponse.setTotalBookingDone(bookingRepository
                    .countTotalBookingByCompanyIdAndTripIdAndStatus(companyId, tripId, Status.Booking.DONE.name()));
            companyResponse.setTotalBookingTimeout(bookingRepository
                    .countTotalBookingByCompanyIdAndTripIdAndStatus(companyId, tripId, Status.Booking.REJECTED.name()));
            companyResponse.setTotalBookingRefunded(bookingRepository
                    .countTotalBookingByCompanyIdAndTripIdAndStatus(companyId, tripId, Status.Booking.REFUNDED.name()));
            companyResponse.setTotalBookingRequestRefund(bookingRepository
                    .countTotalBookingByCompanyIdAndTripIdAndStatus(companyId, tripId, Status.Booking.REQUESTREFUND.name()));
            companyResponse.setTotalBookingAwaitPayment(bookingRepository
                    .countTotalBookingByCompanyIdAndTripIdAndStatus(companyId, tripId, Status.Booking.PAYLATER.name()));
            companyResponse.setTotalRevenue(bookingRepository
                    .getRevenueByCompanyIdAndTripId(companyId, tripId));
            return companyResponse;
        }
    }

    @Override
    public CompanyResponse getCompanyByTripDashboardLast7Days(Long companyId, Long tripId) {
        CompanyResponse companyResponse = new CompanyResponse();
        companyResponse.setTotalCustomer(accountRepository
                .countTotalCustomerByCompanyIdAndTripIdLast7Days(companyId, tripId));
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
