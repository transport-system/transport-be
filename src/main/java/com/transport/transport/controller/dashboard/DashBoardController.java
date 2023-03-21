package com.transport.transport.controller.dashboard;


import com.transport.transport.common.EndpointConstant;
import com.transport.transport.model.entity.Booking;
import com.transport.transport.model.response.dashboard.AdminResponse;
import com.transport.transport.model.response.dashboard.CompanyResponse;
import com.transport.transport.model.response.dashboard.RevenueByMonth;
import com.transport.transport.service.DashBoardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = EndpointConstant.Dashboard.DASHBOARD_ENDPOINT)
@Api( tags = "Dashboard")
public class DashBoardController {
    private final DashBoardService dashBoardService;

    @ApiOperation(value = "Get admin dashboard")
    @PreAuthorize("hasAuthority(T(com.transport.transport.common.RoleEnum).ADMIN)")
    @GetMapping("/admin")
    public AdminResponse getAdminDashboard() {
        return dashBoardService.getAdminDashboard();
    }

    @ApiOperation(value = "Get company dashboard")
    @PreAuthorize("hasAuthority(T(com.transport.transport.common.RoleEnum).COMPANY)")
    @GetMapping("/company/{id}")
    public CompanyResponse getCompanyDashboard(@PathVariable Long id) {
        return dashBoardService.getCompanyDashboard(id);
    }

    @ApiOperation(value = "Get company dashboard by date")
    @PreAuthorize("hasAuthority(T(com.transport.transport.common.RoleEnum).COMPANY)")
    @GetMapping("/company/{id}/{from}/{to}")
    public CompanyResponse getCompanyDashboardByDate(@PathVariable Long id,
                                                     @PathVariable(name = "from") Timestamp from,
                                                     @PathVariable(name = "to") Timestamp to) {
        return dashBoardService.getCompanyDashboard(id, from, to);
    }


    @ApiOperation(value = "Get revenue by month")
    @PreAuthorize("hasAuthority(T(com.transport.transport.common.RoleEnum).COMPANY)")
    @GetMapping("/company/{id}/revenue")
    public List<RevenueByMonth> getRevenueByMonth(@PathVariable Long id) {
        return dashBoardService.getRevenueByMonth(id);
    }
}
