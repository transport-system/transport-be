package com.transport.transport.service;

import com.transport.transport.model.response.dashboard.AdminResponse;
import com.transport.transport.model.response.dashboard.CompanyResponse;
import com.transport.transport.model.response.dashboard.DashboardLast7days;
import com.transport.transport.model.response.dashboard.RevenueByMonth;

import java.sql.Timestamp;
import java.util.List;

public interface DashBoardService<T> {
    AdminResponse getAdminDashboard();
    AdminResponse getAdminDashboardLast7Days();

    CompanyResponse getCompanyDashboard(Long companyId);
    CompanyResponse getCompanyDashboardLast7Days(Long companyId);

    CompanyResponse getCompanyByTripDashboard(Long companyId, Long tripId);
    CompanyResponse getCompanyByTripDashboardLast7Days(Long companyId, Long tripId);

    List<RevenueByMonth> getRevenueByMonth(Long companyId);

    DashboardLast7days getDashboardLast7Days();
}
