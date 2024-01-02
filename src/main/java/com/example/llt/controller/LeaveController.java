package com.example.llt.controller;

import com.alibaba.fastjson.JSON;
import com.example.llt.entity.Leave;
import com.example.llt.entity.Result;
import com.example.llt.service.LeaveService;
import com.example.llt.utils.SnowflakeIdUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/leave")
public class LeaveController {
    private final LeaveService leaveService;

    public LeaveController(LeaveService leaveService) {
        this.leaveService = leaveService;
    }

    @GetMapping
    @ResponseBody
    public String getAllLeaves(){
        Result result = leaveService.getAllLeaveRequest();
        return JSON.toJSONString(result);
    }

    @GetMapping
    @RequestMapping("/cur")
    public String getCurLeaves(HttpServletRequest req, HttpServletResponse resp){
        Long id = (Long)req.getSession().getAttribute("userId");
        List<Leave> leaveList = leaveService.getLeaveById(id);
        req.setAttribute("leaves",leaveList);
        return "pages/employee/leave";
    }


    @GetMapping
    @ResponseBody
    @CrossOrigin
    @RequestMapping("/analysis")
    public String getLeaveAnalysis(){
        Result result = leaveService.getLeaveAnalysis();
        return JSON.toJSONString(result);
    }

    @PostMapping
    public String addLeave(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        String start_date = req.getParameter("start_date");
        String end_date = req.getParameter("end_date");
        String reason = req.getParameter("reason");
        Long userId = (Long) req.getSession().getAttribute("userId");
        String name = (String) req.getSession().getAttribute("name");
        int type = Integer.parseInt(req.getParameter("type"));
        Long id = new SnowflakeIdUtils(1,1,1).nextId();
        Leave leave = new Leave(id, userId, name, start_date, end_date, reason, type,null);
        leaveService.insertLeaveRequest(leave);
        Long useId = (Long)req.getSession().getAttribute("userId");
        ArrayList<Leave> leaveList = leaveService.getLeaveById(useId);
        req.setAttribute("leaves",leaveList);
        return "/pages/employee/leave";
    }

    @PostMapping("/{id}/{status}")
    @ResponseBody
    public String handleLeave(@PathVariable Long id, @PathVariable Integer status){
        Result result = leaveService.checkLeaveRequest(id, status);
        return JSON.toJSONString(result);
    }







}
