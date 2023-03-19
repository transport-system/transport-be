package com.transport.transport.service;

import com.transport.transport.model.response.dashboard.AdminResponse;
import com.transport.transport.model.response.dashboard.CompanyResponse;

import java.math.BigDecimal;
import java.sql.ResultSet;

public interface DashBoardService {
    AdminResponse getAdminDashboard();
    CompanyResponse getCompanyDashboard(Long companyId);
}
