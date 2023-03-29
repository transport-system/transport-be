package com.transport.transport.controller.dashboard;


import com.transport.transport.common.EndpointConstant;
import com.transport.transport.model.response.dashboard.AdminResponse;
import com.transport.transport.model.response.dashboard.CompanyResponse;
import com.transport.transport.model.response.dashboard.RevenueByMonth;
import com.transport.transport.model.response.dashboard.RevenueLast7Days;
import com.transport.transport.service.DashBoardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.List;

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

    @ApiOperation(value = "Get admin dashboard last 7 days")
    @PreAuthorize("hasAuthority(T(com.transport.transport.common.RoleEnum).ADMIN)")
    @GetMapping("/admin/7days")
    public AdminResponse getAdminDashboardLast7Days() {
        return dashBoardService.getAdminDashboardLast7Days();
    }

    @ApiOperation(value = "Get company dashboard")
    @PreAuthorize("hasAuthority(T(com.transport.transport.common.RoleEnum).COMPANY)")
    @GetMapping("/company/{id}")
    public CompanyResponse getCompanyDashboard(@PathVariable Long id) {
        return dashBoardService.getCompanyDashboard(id);
    }

    @ApiOperation(value = "Get company dashboard last 7 days")
    @PreAuthorize("hasAuthority(T(com.transport.transport.common.RoleEnum).COMPANY)")
    @GetMapping("/company/7days/{id}")
    public CompanyResponse getCompanyDashboardLast7Days(@PathVariable Long id) {
        return dashBoardService.getCompanyDashboardLast7Days(id);
    }

    @ApiOperation(value = "Get company dashboard by trip")
    @PreAuthorize("hasAuthority(T(com.transport.transport.common.RoleEnum).COMPANY)")
    @GetMapping("/company/{id}/trip/{tripId}")
    public CompanyResponse getCompanyByTripDashboard(@PathVariable Long id, @PathVariable Long tripId) {
        return dashBoardService.getCompanyByTripDashboard(id, tripId);
    }

//    @ApiOperation(value = "Get company dashboard by trip last 7 days")
//    @PreAuthorize("hasAuthority(T(com.transport.transport.common.RoleEnum).COMPANY)")
//    @GetMapping("/company/7days/{id}/trip/{tripId}")
//    public CompanyResponse getCompanyByTripDashboardLast7Days(@PathVariable Long id, @PathVariable Long tripId) {
//        return dashBoardService.getCompanyByTripDashboardLast7Days(id, tripId);
//    }

    @ApiOperation(value = "Get revenue by month")
    @PreAuthorize("hasAuthority(T(com.transport.transport.common.RoleEnum).COMPANY)")
    @GetMapping("/company/{id}/revenue")
    public List<RevenueByMonth> getRevenueByMonth(@PathVariable Long id) {
        return dashBoardService.getRevenueByMonth(id);
    }

    @ApiOperation(value = "Get <T> by last 7 days")
    @GetMapping("/last7days")
    public <T> T getLast7Days(Class<T> clazz) {
        return (T) dashBoardService.getDashboardLast7Days();
    }

    @ApiOperation(value = "Get <T> last 7 days by company")
    @GetMapping("/last7days/{id}")
    public <T> T getLast7DaysByCompany(@PathVariable Long id, Class<T> clazz) {
        return (T) dashBoardService.getDashboardLast7DaysByCompany(id);
    }

    @ApiOperation(value = "Get revenue last 7 days")
    @GetMapping("/revenue/last7days")
    public RevenueLast7Days getRevenueLast7Days() {
        return dashBoardService.getRevenueLast7Days();
    }

    @ApiOperation(value = "Get revenue last 7 days by company")
    @GetMapping("/revenue/last7days/{id}")
    public RevenueLast7Days getRevenueLast7Days(@PathVariable Long id) {
        return dashBoardService.getRevenueLast7Days(id);
    }
}
