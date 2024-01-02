package com.example.llt.controller;


import com.example.llt.service.ClockInService;
import com.example.llt.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/test")
public class ScheduleController {

    private final EmployeeService employeeService;

    private final ClockInService clockInService;

    public ScheduleController(ClockInService clockInService, EmployeeService employeeService) {
        this.clockInService = clockInService;
        this.employeeService = employeeService;
    }

    @Scheduled(cron = "0 0 12 ? * MON-FRI")
    public void test(){
        Integer employeeCount = employeeService.getEmployeeCount();
        Integer todayClockInCount = clockInService.getTodayClockInCount();

        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateformat.format(new Date());

        Double rate = todayClockInCount * 1.0 / employeeCount;
        clockInService.insertClockInRate(date, rate);
    }

}