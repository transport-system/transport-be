package com.transport.transport.controller.dashboard;


import com.transport.transport.common.EndpointConstant;
import com.transport.transport.service.DashBoardService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = EndpointConstant.Dashboard.DASHBOARD_ENDPOINT)
@Api( tags = "Dashboard")
public class DashBoardController {

    private final DashBoardService dashBoardService;
    @GetMapping("/countUser")
    public int countUser(){
        return dashBoardService.countUser();
    }

    @GetMapping("/countCompany")
    public int countCompany(){
        return dashBoardService.countCompany();
    }

    @GetMapping("/countTrip")
    public int countTrip(){
        return dashBoardService.countTrip();
    }

    @GetMapping("/countBookingByCompany")
    public int countBookingByCompany(){
        return dashBoardService.countBookingByCompany(1L);
    }

    @GetMapping("/countBookingByTrip")
    public int countBookingByTrip(){
        return dashBoardService.countBookingByTrip(1L);
    }

    @GetMapping("/revenue")
    public BigDecimal revenue(){
        return dashBoardService.revenue();
    }

    @GetMapping("/revenueByCompany")
    public BigDecimal revenueByCompany(){
        return dashBoardService.revenueByCompany();
    }
}
