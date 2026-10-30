package com.auca.foodordering.controller;

import com.auca.foodordering.service.DashboardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    // BONUS - GET /api/dashboard/summary
    @GetMapping("/summary")
    public ResponseEntity<Map<String, Long>> getSummary() {
        return new ResponseEntity<>(dashboardService.getSummary(), HttpStatus.OK);
    }
}