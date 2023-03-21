package com.transport.transport.service;

import com.transport.transport.model.response.dashboard.AdminResponse;
import com.transport.transport.model.response.dashboard.CompanyResponse;
import com.transport.transport.model.response.dashboard.RevenueByMonth;

import java.sql.Timestamp;
import java.util.List;

public interface DashBoardService {
    AdminResponse getAdminDashboard();


    CompanyResponse getCompanyDashboard(Long companyId);
    CompanyResponse getCompanyDashboard(Long companyId, Timestamp from, Timestamp to);


    List<RevenueByMonth> getRevenueByMonth(Long companyId);
}
