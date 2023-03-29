package com.transport.transport.service.impl.dashboard;

import com.transport.transport.common.PaymentType;
import com.transport.transport.common.RoleEnum;
import com.transport.transport.common.Status;
import com.transport.transport.exception.NotFoundException;
import com.transport.transport.model.entity.Booking;
import com.transport.transport.model.response.dashboard.*;
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
    private final VoucherRepository voucherRepository;

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

            companyResponse.setTotalCustomer(bookingRepository.countBookingsByAccountCompanyId(companyId));
            companyResponse.setTotalTrip(tripRepository.countTripsByCompanyIdAndStatus(companyId,Status.Trip.ACTIVE.name()));
            companyResponse.setTotalVehicle(vehicleRepository.countVehicleByCompanyId(companyId));

            companyResponse.setTotalBooking(bookingRepository.countBookingsByAccountCompanyId(companyId));


            companyResponse.setTotalBookingRejected(bookingRepository.countBookingsByAccountCompanyIdAndStatus(companyId,
                    Status.Booking.REJECTED.name()));
            companyResponse.setTotalBookingPending(bookingRepository.countBookingsByAccountCompanyIdAndStatus(companyId,
                    Status.Booking.PENDING.name()));
            companyResponse.setTotalBookingDone(bookingRepository.countBookingsByAccountCompanyIdAndStatus(companyId,
                    Status.Booking.DONE.name()));
            companyResponse.setTotalBookingTimeout(bookingRepository.countBookingsByAccountCompanyIdAndStatus(companyId,
                    Status.Booking.REJECTED.name()));
            companyResponse.setTotalBookingRefunded(bookingRepository.countBookingsByAccountCompanyIdAndStatus(companyId,
                    Status.Booking.REFUNDED.name()));
            companyResponse.setTotalBookingRequestRefund(bookingRepository.countBookingsByAccountCompanyIdAndStatus(companyId,
                    Status.Booking.REQUESTREFUND.name()));
            companyResponse.setTotalBookingAwaitPayment(bookingRepository.countBookingsByAccountCompanyIdAndStatus(companyId,
                    Status.Booking.PAYLATER.name()));

            companyResponse.setTotalBookingPaymentCard(bookingRepository.countBookingsByAccountCompanyIdAndPaymentMethod(companyId, PaymentType.CARD.name()));
            companyResponse.setTotalBookingPaymentCash(bookingRepository.countBookingsByAccountCompanyIdAndPaymentMethod(companyId, PaymentType.CASH.name()));

            companyResponse.setTotalRevenue(bookingRepository.countBookingsByTotalPriceAndaAndAccountCompanyIdAnAndStatus(companyId));


            companyResponse.setTotalVoucherCompanyHave(voucherRepository.countVoucherByCompanyId(companyId));
            companyResponse.setTotalVoucherIsBooked(voucherRepository.countVouchersByBookings(companyId));
            return companyResponse;
        }
    }

    @Override
    public CompanyResponse getCompanyDashboardLast7Days(Long companyId) {
        if (companyRepository.findById(companyId).isEmpty()) {
            throw new NotFoundException("Company not found");
        } else if (accountRepository.countTotalCustomerByCompanyIdLast7Days(companyId) == 0) {
            throw new NotFoundException("Date khong ton tai trong 7day ");
        } else {
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
            companyResponse.setTotalCustomer(accountRepository.countAccountsByCompanyIdAndTripId(companyId, tripId));
            companyResponse.setTotalVehicle(tripRepository.countVehicleByCompanyIdAndTripId(companyId,tripId));
            companyResponse.setTotalTrip(tripRepository.countTripsByCompanyIdAndStatus(companyId, Status.Trip.ACTIVE.name()));
            companyResponse.setTotalBooking(bookingRepository.countBookingByCompanyIdAndTripId(companyId, tripId));
            companyResponse.setTotalBookingPending(bookingRepository.countBookingByTripCompanyIdAndTripAndStatus(companyId, tripId, Status.Booking.PENDING.name()));
            companyResponse.setTotalBookingDone(bookingRepository.countBookingByTripCompanyIdAndTripAndStatus(companyId, tripId, Status.Booking.DONE.name()));
            companyResponse.setTotalBookingTimeout(bookingRepository.countBookingByTripCompanyIdAndTripAndStatus(companyId, tripId, Status.Booking.REJECTED.name()));
            companyResponse.setTotalBookingRefunded(bookingRepository.countBookingByTripCompanyIdAndTripAndStatus(companyId, tripId, Status.Booking.REFUNDED.name()));
            companyResponse.setTotalBookingRequestRefund(bookingRepository.countBookingByTripCompanyIdAndTripAndStatus(companyId, tripId, Status.Booking.REQUESTREFUND.name()));
            companyResponse.setTotalBookingAwaitPayment(bookingRepository.countBookingByTripCompanyIdAndTripAndStatus(companyId, tripId, Status.Booking.PAYLATER.name()));
            companyResponse.setTotalRevenue(bookingRepository.getRevenueByCompanyIdAndTripId(companyId, tripId));
            companyResponse.setTotalBookingPaymentCash(bookingRepository.countBookingByTripCompanyIdAndTripIdAndMAndPaymentMethod(companyId,tripId,PaymentType.CASH.name()));
            companyResponse.setTotalBookingPaymentCard(bookingRepository.countBookingByTripCompanyIdAndTripIdAndMAndPaymentMethod(companyId,tripId,PaymentType.CARD.name()));

            companyResponse.setTotalVoucherCompanyHave(voucherRepository.countVoucherByCompanyId(companyId)); // k có bảng cột trip voucher nên chỉ lấy tổng company th
            companyResponse.setTotalVoucherisBookedinTripId(voucherRepository.countVouchersByCompanyIdAndTripId(companyId,tripId));
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

    @Override
    public DashboardLast7days getDashboardLast7Days() {
        List<Integer> list = bookingRepository.getAllBookingLast7Days();
        DashboardLast7days dashboardLast7days = new DashboardLast7days();
        dashboardLast7days.setTotalBooking(list);
        return dashboardLast7days;
    }

    @Override
    public DashboardLast7days getDashboardLast7DaysByCompany(Long companyId) {
        List<Integer> list = bookingRepository.getAllBookingLast7DaysByCompanyId(companyId);
        DashboardLast7days dashboardLast7days = new DashboardLast7days();
        dashboardLast7days.setTotalBooking(list);
        return dashboardLast7days;
    }

    @Override
    public RevenueLast7Days getRevenueLast7Days() {
        List<BigDecimal> list = bookingRepository.revenueByAdmin();
        RevenueLast7Days revenueLast7Days = new RevenueLast7Days();
        revenueLast7Days.setRevenue(list);
        return revenueLast7Days;
    }

    @Override
    public RevenueLast7Days getRevenueLast7Days(Long companyId) {
        List<BigDecimal> list = bookingRepository.revenueByCompanyId(companyId);
        RevenueLast7Days revenueLast7Days = new RevenueLast7Days();
        revenueLast7Days.setRevenue(list);
        return revenueLast7Days;
    }


}
