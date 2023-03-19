package com.transport.transport.controller.dashboard;


import com.transport.transport.common.EndpointConstant;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = EndpointConstant.Dashboard.DASHBOARD_ENDPOINT)
@Api( tags = "Dashboard")
public class DashBoardController {

}
